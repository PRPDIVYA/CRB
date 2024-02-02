package com.org.conference.room.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.conference.room.entity.ConferenceRoom;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

	@Query(value = "select * from (select * from conference_room as ro where ro.id not in "
			+ "(select re.room_id from reservation as re where (reservation_from >:endTime and (:startTime between reservation_from and reservation_till )) "
			+ "or (reservation_till >:startTime and (:endTime between reservation_from and reservation_till )) )) as roo where roo.id = :roomId", nativeQuery = true)
	public ConferenceRoom checkAvailability(LocalDateTime startTime, LocalDateTime endTime, Long roomId);

	@Query(value = "select * from conference_room r where r.id not in ("
			+ "select res.room_id from reservation res where ("
			+ "res.reservation_from <= :de and res.reservation_till >= :db" + "))", nativeQuery = true)
	public List<ConferenceRoom> findAvailableRooms(LocalDateTime db, LocalDateTime de);
	

	@Query(value = "select * from conference_room r where r.id not in ("
			+ "select res.room_id from reservation res where ("
			+ "res.reservation_from < :de and res.reservation_till > :db" + ")) and r.capacity>=:count", nativeQuery = true)
	public List<ConferenceRoom> findAvailableRoomsByCapacityGreterThanEqual(LocalDateTime db, LocalDateTime de,Long count);

	public List<ConferenceRoom> findByCapacityGreaterThanEqual(Long count);
	
	@Query(value = "select * from (select * from conference_room as ro where ro.id not in "
			+ "(select re.room_id from Room_Maintenance as re where (startTime > :startTime and startTime <:endTime) "
			+ "or (endTime >:startTime and endTime < :endTime))) as roo where roo.id = :roomId", nativeQuery = true)
	public ConferenceRoom checkForMaintenance(LocalTime startTime, LocalTime endTime, Long roomId);

}
