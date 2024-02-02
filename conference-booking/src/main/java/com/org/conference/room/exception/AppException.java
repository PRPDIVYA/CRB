package com.org.conference.room.exception;

import com.org.conference.room.common.AppErrorCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AppException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4531329980806756352L;
	private String errorDetails;
	private String errorCode;

	public AppException (Exception ex) {
		super(ex);
	}
	
	public AppException (Exception ex, String errorCode) {
		super(ex);
		this.errorCode=errorCode;
	}
	public AppException(AppErrorCode appErrorCode,String message) {
		super(message);
		this.errorCode=appErrorCode.getErrorCode();
		this.errorDetails=message;
	}
	
	public AppException (AppErrorCode appErrorCode)
	{
		super(appErrorCode.getErrorMessage());
		this.errorCode=appErrorCode.getErrorCode();
		this.errorDetails=appErrorCode.getErrorMessage();
		
	}
	public AppException(Exception ex , AppErrorCode appErrorCode, String customMessage)
	{
		super(ex);
		this.errorCode=appErrorCode.getErrorCode();
		this.errorDetails=customMessage;
	}
}
