package com.org.conference.room.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ConferenceRoomExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String APPLICATION_EXCEPTION_OCCURED="Something went wrong !";
	private static final String FAILED=" Request failed";
	
	@ExceptionHandler({AppException.class})
	public ModelAndView applicationExceptionAdvice(AppException appException) {
		log.error("Business exception occured..{}",appException);
		String message;
		Object errorData;
		if(appException!=null && appException.getMessage()!=null) {
			log.info("extract error message from exception");
			message=appException.getErrorCode();
			errorData=appException.getErrorDetails();
		}
		else {
			message = APPLICATION_EXCEPTION_OCCURED;
			errorData=FAILED;
		}
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String,Object> errorMap = new HashMap<>();
		errorMap.put(message, errorData);
		view.setAttributesMap(errorMap);
		return new ModelAndView(view);
	}

}
