package com.org.conference.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.conference.room.entity.Reservation;

public interface ReservationsRepository extends JpaRepository<Reservation, Long>{

}
