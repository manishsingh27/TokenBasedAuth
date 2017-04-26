package com.adms.authz.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {

	private List<FieldErrorDTO> fieldErrors = new ArrayList<>();


	public void addFieldError(String field, String message) {
		FieldErrorDTO error = new FieldErrorDTO(field, message);
		fieldErrors.add(error);
	}
	
	public void addFieldError(String field, int errorCode, String message) {
		FieldErrorDTO error = new FieldErrorDTO(field, errorCode, message);
		fieldErrors.add(error);
	}

	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}
	
	
}
