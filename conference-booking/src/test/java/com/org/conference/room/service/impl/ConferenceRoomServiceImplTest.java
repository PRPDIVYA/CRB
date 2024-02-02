package com.org.conference.room.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.org.conference.room.dto.ConferenceRoomDto;
import com.org.conference.room.dto.ReservationDto;
import com.org.conference.room.entity.ConferenceRoom;
import com.org.conference.room.exception.AppException;
import com.org.conference.room.repository.ConferenceRoomRepository;

@ExtendWith(MockitoExtension.class)
class ConferenceRoomServiceImplTest {

    @Mock
    private ConferenceRoomRepository mockConfRoomRepository;
    @Mock
    private ModelMapper mockMapper;

    @InjectMocks
    private ConferenceRoomServiceImpl mockConferenceRoomServiceImpl;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mockConferenceRoomServiceImpl, "intervalMinutes", 15L);
    }

    @Test
    void testGetAvailableRoomsForTimeWindow() {
        final ConferenceRoomDto conferenceRoomDto = new ConferenceRoomDto();
        conferenceRoomDto.setId(1L);
        conferenceRoomDto.setName("Blooms");
        conferenceRoomDto.setCapacity(4);
        final List<ConferenceRoomDto> finalresult = List.of(conferenceRoomDto);

        final ConferenceRoom conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1L);
        conferenceRoom.setName("Blooms");
        conferenceRoom.setCapacity(4);
        conferenceRoom.setCreatedon(LocalDateTime.of(2024, 1, 26, 10, 30, 0));
        final List<ConferenceRoom> conferenceRooms = List.of(conferenceRoom);
        when(mockConfRoomRepository.findAvailableRooms(LocalDateTime.of(2024, 1, 27, 10, 30, 0),
                LocalDateTime.of(2024, 1, 27, 11, 30, 0))).thenReturn(conferenceRooms);
        
        when(mockMapper.map(conferenceRoom, ConferenceRoomDto.class)).thenReturn(conferenceRoomDto);

        List<ConferenceRoomDto> result = mockConferenceRoomServiceImpl.getAvailableRoomsForTimeWindow(
                LocalDateTime.of(2024, 1, 27, 10, 30, 0), LocalDateTime.of(2024, 1, 27, 11, 30, 0));
        assertEquals(finalresult, result);
    }

    @Test
    void testFetchAllConferenceRooms() {
        final ConferenceRoomDto conferenceRoomDto = new ConferenceRoomDto();
        conferenceRoomDto.setId(2L);
        conferenceRoomDto.setName("vintage");
        conferenceRoomDto.setCapacity(9);
        final List<ConferenceRoomDto> expectedResult = List.of(conferenceRoomDto);

        final ConferenceRoom conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(2L);
        conferenceRoom.setName("vintage");
        conferenceRoom.setCapacity(9);
        conferenceRoom.setCreatedon(LocalDateTime.of(2024, 1, 26, 10, 30, 0));
        final List<ConferenceRoom> conferenceRooms = List.of(conferenceRoom);
        when(mockConfRoomRepository.findAll()).thenReturn(conferenceRooms);
        when(mockMapper.map(conferenceRoom, ConferenceRoomDto.class)).thenReturn(conferenceRoomDto);

        final List<ConferenceRoomDto> result = mockConferenceRoomServiceImpl.fetchAllConferenceRooms();
        assertEquals(expectedResult, result);
    }

    

     @Test
    void testCreateReservationException() {
        final ReservationDto reservationReq = new ReservationDto();
        reservationReq.setReservationId(0L);
        reservationReq.setRoomId(0L);
        reservationReq.setReservationFrom(LocalDateTime.now());
        reservationReq.setReservationTill(LocalDateTime.now().plusMinutes(15));
        reservationReq.setPeopleCount(3L);

        final ReservationDto expectedResult = new ReservationDto();
        expectedResult.setReservationId(1L);
        expectedResult.setRoomId(1L);
        expectedResult.setReservationFrom(LocalDateTime.now());
        expectedResult.setReservationTill(LocalDateTime.now().plusMinutes(15));
        expectedResult.setPeopleCount(3L);

        final ConferenceRoom conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1L);
        conferenceRoom.setName("Blooms");
        conferenceRoom.setCapacity(5);
        conferenceRoom.setCreatedon(LocalDateTime.of(2024, 1, 25, 10, 30, 0));
        final ConferenceRoom conferenceRoom2 = new ConferenceRoom();
        conferenceRoom2.setId(2L);
        conferenceRoom2.setName("vintage");
        conferenceRoom2.setCapacity(9);
        conferenceRoom2.setCreatedon(LocalDateTime.of(2024, 1, 25, 10, 30, 0));
        assertThrows(AppException.class,()-> mockConferenceRoomServiceImpl.createReservation(reservationReq));
    }

    @Test
    void testCreateReservation_ConferenceRoomRepositoryFindAvailableRoomsByCapacityGreterThanEqualReturnsNoItems() {
        final ReservationDto reservationReq = new ReservationDto();
        reservationReq.setReservationId(0L);
        reservationReq.setRoomId(0L);
        reservationReq.setReservationFrom(LocalDateTime.of(2024, 1, 25, 0, 0, 0));
        reservationReq.setReservationTill(LocalDateTime.of(2024, 1, 25, 0, 0, 0));
        reservationReq.setPeopleCount(10L);

        assertThrows(AppException.class, () -> mockConferenceRoomServiceImpl.createReservation(reservationReq));
    }

   
}
