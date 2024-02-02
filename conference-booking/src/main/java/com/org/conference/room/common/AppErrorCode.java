package com.org.conference.room.common;

public enum AppErrorCode {

	SYSTEM_ERROR("SERVICE-ERROR-000", "Something went wrong with the service"),
	RESERVATION_ENDTIME_ERROR("SERVICE-ERROR-001","Reservation end time should always later than start time"),
	RESERVATION_INTERVAL_ERROR("SERVICE-ERROR-002","Reservation can be done only in intervals of 15 mins time.For ex : 2:00-2:15 or 2:00-2:30 0r 2:00-3:00. Please change conference room reservation timings."),
	RESERVATION_DATE_TIME_ERROR("SERVICE-ERROR-003","Reservation can be done only for current date and cannot be done for future or past date times"),
	MAINTENANCE_OVERLAP_ERROR("SERVICE-ERROR-004","Reservation overlaps with conference room maintenance. Maintanance timings will be (9:00-9:15,13:00-13:15,17:00-17:15). Please change reservation timings."),
	ROOM_NOTAVAILABLE_EXCEPTION ("SERVICE-ERROR-005" ,"Conference Room are unavailable for the booking slot");
	private String errorCode;
	private String errorMessage;

	AppErrorCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
