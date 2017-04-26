package com.adms.authz.util;

import com.adms.authz.exception.ValidationErrorDTO;

public class ResponseApi {
	
	private boolean hasError;
	
	private Object payload;
	
	private ValidationErrorDTO error;
		

	public ResponseApi(boolean hasError, Object payload, ValidationErrorDTO error) {
		super();
		this.hasError = hasError;
		this.payload = payload;
		this.error = error;
	}

	public boolean isHasError() {
		return hasError;
	}

	public Object getPayload() {
		return payload;
	}

	public ValidationErrorDTO getError() {
		return error;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public void setError(ValidationErrorDTO error) {
		this.error = error;
	}
	
	

}
