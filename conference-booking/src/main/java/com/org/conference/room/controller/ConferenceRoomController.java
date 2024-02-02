package com.org.conference.room.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.conference.room.common.CommonUtil;
import com.org.conference.room.dto.ConferenceRoomDto;
import com.org.conference.room.dto.ReservationDto;
import com.org.conference.room.dto.Response;
import com.org.conference.room.service.ConferenceRoomService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/conference")
@Validated
public class ConferenceRoomController {
	@Autowired
	private ConferenceRoomService conferenceRoomBookingService;
	

	@GetMapping("/rooms")
	public ResponseEntity<Response<Object>> fetchAllRooms() {

		List<ConferenceRoomDto> roomLst = conferenceRoomBookingService.fetchAllConferenceRooms();
		return ResponseEntity.ok(CommonUtil.buildSuccessResponse(roomLst));
	}

	@GetMapping("/availableRooms")
	public  ResponseEntity<Response<Object>> fetchAvailableRoomsForTimeWindow( 
			@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endTime) {
		List<ConferenceRoomDto> roomLst = conferenceRoomBookingService.getAvailableRoomsForTimeWindow(startTime,endTime);
		return ResponseEntity.ok(CommonUtil.buildSuccessResponse(roomLst));
	}
	
	@PostMapping(value = "/reserve", headers = "Accept=application/json")
	public ResponseEntity<Response<Object>> createReservation( @Valid @RequestBody ReservationDto reservation) {
		log.info("Creating Reservation {}" , reservation);
		ReservationDto response = conferenceRoomBookingService.createReservation(reservation);
		log.info("Reservation response{}" , response);
		return ResponseEntity.ok(CommonUtil.buildSuccessResponse(response));
	}
}
