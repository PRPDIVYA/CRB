package com.org.conference.room.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Reservation")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "reservation_from")
    private LocalDateTime reservationFrom;

    @Column(name = "reservation_till")
    private LocalDateTime reservationTill;

    @Column(name = "people_count")
    private Long peopleCount;
    @Column(name = "created_on")
	private LocalDateTime reservedon;

	@PrePersist
	public void prePersist() {
		setReservedon(LocalDateTime.now());
	}
}
