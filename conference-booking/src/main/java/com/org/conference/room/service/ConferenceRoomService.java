package com.org.conference.room.service;

import java.time.LocalDateTime;
import java.util.List;

import com.org.conference.room.dto.ConferenceRoomDto;
import com.org.conference.room.dto.ReservationDto;


public interface ConferenceRoomService {

	public List<ConferenceRoomDto> getAvailableRoomsForTimeWindow(LocalDateTime startTime , LocalDateTime endTime);
	
	public List<ConferenceRoomDto>fetchAllConferenceRooms();
	
	public ReservationDto createReservation(ReservationDto reservation);
	
}
