package com.org.conference.room.dto;


import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationDto {

    private Long reservationId;
    private Long roomId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime reservationFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime reservationTill;
    @Min(value=2,message="The number of people allowed for booking should be greater than 1")
    private Long peopleCount;
    private LocalDateTime reservedon;

}
