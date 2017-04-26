package com.adms.authz.exception;

/**
 * @author manis
 *
 */
public class FieldErrorDTO {
	private String field;
	
	private int errorCode;

	private String message;

	public FieldErrorDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	

	public FieldErrorDTO(String field, int errorCode, String message) {
		super();
		this.field = field;
		this.errorCode = errorCode;
		this.message = message;
	}



	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	
	

}
