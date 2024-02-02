package com.org.conference.room.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ConferenceRoomDto {

	private Long id;
	private String name;
	private int capacity;
}
