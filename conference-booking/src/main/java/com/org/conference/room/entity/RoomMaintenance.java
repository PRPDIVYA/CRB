package com.org.conference.room.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ROOM_MAINTENANCE")
public class RoomMaintenance {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;

    @Column(name = "room_id")
    private Long roomId;
    
    @Column(name = "starttime")
    private LocalTime startTime;

    @Column(name = "endtime")
    private LocalTime endTime;
    
    @Column(name = "created_on")
	private LocalDateTime createdon;

}
