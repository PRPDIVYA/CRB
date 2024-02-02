package com.org.conference.room.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.org.conference.room.common.AppErrorCode;
import com.org.conference.room.dto.ConferenceRoomDto;
import com.org.conference.room.dto.ReservationDto;
import com.org.conference.room.entity.ConferenceRoom;
import com.org.conference.room.entity.Reservation;
import com.org.conference.room.exception.AppException;
import com.org.conference.room.repository.ConferenceRoomRepository;
import com.org.conference.room.repository.ReservationsRepository;
import com.org.conference.room.service.ConferenceRoomService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

	@Autowired
	private ConferenceRoomRepository confRoomRepository;
	@Autowired
	private ReservationsRepository reservationsRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Value("${props.interval.minutes}")
	private Long intervalMinutes;

	@Override
	public List<ConferenceRoomDto> getAvailableRoomsForTimeWindow(LocalDateTime startTime, LocalDateTime endTime) {

		List<ConferenceRoom> availableRooms = confRoomRepository.findAvailableRooms(startTime, endTime);

		return availableRooms.stream().map(room -> mapper.map(room, ConferenceRoomDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ConferenceRoomDto> fetchAllConferenceRooms() {
		log.info("fetching all conference rooms info");
		List<ConferenceRoomDto> conferenceLst = confRoomRepository.findAll().stream()
				.map(room -> mapper.map(room, ConferenceRoomDto.class)).collect(Collectors.toList());
		return conferenceLst;
	}

	@Override
	public ReservationDto createReservation(ReservationDto reservationReq) {
		validateReservationRequest(reservationReq);
		 List<ConferenceRoom> roomList =confRoomRepository. findAvailableRoomsByCapacityGreterThanEqual(reservationReq.getReservationFrom(),reservationReq.getReservationTill(),reservationReq.getPeopleCount());
		 Optional<ConferenceRoom> availableRoom = roomList.stream().findFirst();
		 log.info("Available Room to book: " + availableRoom);
		if (availableRoom.isPresent()) {
			log.info("check for Maintenance of room: " + reservationReq);
			checkForRoomMaintenance(reservationReq, availableRoom.get().getId());
			reservationReq.setRoomId(availableRoom.get().getId());
			Reservation reservation = reservationsRepository.save(mapper.map(reservationReq, Reservation.class));
			return mapper.map(reservation, ReservationDto.class);
		} else {
			throw new AppException(AppErrorCode.ROOM_NOTAVAILABLE_EXCEPTION);}}

	private void validateReservationRequest(ReservationDto request) {
		log.info("Validating the reservation request {}"+request);
		LocalDate currentDate = LocalDate.now();
		LocalDate reservationStartDt = request.getReservationFrom().toLocalDate();
		LocalDate reservationEndDt = request.getReservationTill().toLocalDate();
		LocalTime reservationStartTime = request.getReservationFrom().toLocalTime();
		LocalTime reservationEndTime = request.getReservationTill().toLocalTime();

		if (Period.between(currentDate, reservationStartDt).isZero()&& Period.between(currentDate, reservationEndDt).isZero()) {
			
			if ((Duration.between(request.getReservationFrom(), request.getReservationTill()).isZero())|| (Duration.between(request.getReservationFrom(), request.getReservationTill()).isNegative())) {
				 throw  new AppException(AppErrorCode.RESERVATION_ENDTIME_ERROR);                    
			} else if ((Duration.between(request.getReservationFrom(), request.getReservationTill()).toMinutes())% intervalMinutes!= 0 || reservationStartTime.getMinute()%intervalMinutes !=0 || reservationEndTime.getMinute() % intervalMinutes !=0) {
				throw new AppException(AppErrorCode.RESERVATION_INTERVAL_ERROR);
			}
		} else {
			throw new AppException(AppErrorCode.RESERVATION_DATE_TIME_ERROR);
		}

	}


	private void checkForRoomMaintenance(ReservationDto request, Long roomId) {
		log.info("checking for maintenance timings overlap for request {}"+request);
		ConferenceRoom room = confRoomRepository.checkForMaintenance(request.getReservationFrom().toLocalTime(),
				request.getReservationTill().toLocalTime(), roomId);
		if (Objects.isNull(room)) {
			throw new AppException(AppErrorCode.MAINTENANCE_OVERLAP_ERROR);
		}
	}
}
