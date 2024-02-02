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
@Data
@Table(name="CONFERENCE_ROOM")
public class ConferenceRoom  {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String name;
	private int capacity; 
	@Column(name = "created_on")
	private LocalDateTime createdon;
    @Column(name = "updated_on")
	private LocalDateTime updatedon;

	@PrePersist
	public void prePersist() {
		setCreatedon(LocalDateTime.now());
	}

}
	

