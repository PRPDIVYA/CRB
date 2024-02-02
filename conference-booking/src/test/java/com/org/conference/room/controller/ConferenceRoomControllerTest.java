package com.org.conference.room.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.org.conference.room.dto.ConferenceRoomDto;
import com.org.conference.room.dto.ReservationDto;
import com.org.conference.room.dto.Response;
import com.org.conference.room.service.ConferenceRoomService;

@ExtendWith(MockitoExtension.class)
class ConferenceRoomControllerTest {

    @Mock
    private ConferenceRoomService mockConferenceRoomBookingService;

    @InjectMocks
    private ConferenceRoomController conferenceRoomControllerTest;

    @Test
    void testFetchAllRooms() {
        ConferenceRoomDto conferenceRoomDto = new ConferenceRoomDto();
        conferenceRoomDto.setId(1L);
        conferenceRoomDto.setName("Blooms");
        conferenceRoomDto.setCapacity(4);
        List<ConferenceRoomDto> conferenceRoomDtos = List.of(conferenceRoomDto);
        when(mockConferenceRoomBookingService.fetchAllConferenceRooms()).thenReturn(conferenceRoomDtos);
        ResponseEntity<Response<Object>> result = conferenceRoomControllerTest.fetchAllRooms();
        assertNotNull(result);
    }

    @Test
    void testFetchAllRoomsReturnsNoItems() {
        when(mockConferenceRoomBookingService.fetchAllConferenceRooms()).thenReturn(Collections.emptyList());
        ResponseEntity<Response<Object>> result = conferenceRoomControllerTest.fetchAllRooms();
        assertNotNull(result);
    }

    @Test
    void testFetchAvailableRoomsForTimeWindow() {
        final ConferenceRoomDto conferenceRoomDto = new ConferenceRoomDto();
        conferenceRoomDto.setId(1L);
        conferenceRoomDto.setName("Vintage");
        conferenceRoomDto.setCapacity(9);
        List<ConferenceRoomDto> conferenceRoomDtos = List.of(conferenceRoomDto);
        when(mockConferenceRoomBookingService.getAvailableRoomsForTimeWindow(LocalDateTime.of(2024, 1, 26, 10, 30, 0),
                LocalDateTime.of(2024, 1, 26, 11, 0, 0))).thenReturn(conferenceRoomDtos);
        ResponseEntity<Response<Object>> result = conferenceRoomControllerTest.fetchAvailableRoomsForTimeWindow(
                LocalDateTime.of(2024, 1, 26, 10, 30, 0), LocalDateTime.of(2024, 1, 26, 11, 0, 0));
        assertNotNull(result);
    }

    @Test
    void testCreateReservation() {
        final ReservationDto reservation = new ReservationDto();
        reservation.setReservationId(0L);
        reservation.setRoomId(0L);
        reservation.setReservationFrom(LocalDateTime.of(2020, 1, 26, 10, 30, 0));
        reservation.setReservationTill(LocalDateTime.of(2020, 1, 26, 11, 0, 0));
        reservation.setPeopleCount(5L);
        
        final ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservationId(1L);
        reservationDto.setRoomId(2L);
        reservationDto.setReservationFrom(LocalDateTime.of(2020, 1, 26, 10, 30, 0));
        reservationDto.setReservationTill(LocalDateTime.of(2020, 1, 26, 11, 0, 0));
        reservationDto.setPeopleCount(5L);
        final ReservationDto reservationreq = new ReservationDto();
        reservationreq.setReservationId(0L);
        reservationreq.setRoomId(0L);
        reservationreq.setReservationFrom(LocalDateTime.of(2020, 1, 26, 10, 30, 0));
        reservationreq.setReservationTill(LocalDateTime.of(2020, 1, 26, 11, 0, 0));
        reservationreq.setPeopleCount(5L);
        when(mockConferenceRoomBookingService.createReservation(reservationreq)).thenReturn(reservationDto);

        ResponseEntity<Response<Object>> result = conferenceRoomControllerTest.createReservation(
                reservation);
        assertEquals("SUCCESS", result.getBody().getStatus());
    }
}
