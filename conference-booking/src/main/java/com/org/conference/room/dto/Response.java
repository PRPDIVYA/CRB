package com.org.conference.room.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
	private String status;
    private Object data;
    }
