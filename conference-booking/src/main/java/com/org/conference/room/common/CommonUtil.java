package com.org.conference.room.common;

import com.org.conference.room.dto.Response;

public class CommonUtil {
	private static final String SUCCESS="SUCCESS";
	public static Response<Object>buildSuccessResponse(Object data){
		return Response.builder().status(SUCCESS).data(data).build();
	}

}
