package com.eduguide.responses;

public class RegistrationResponse {
	private String message;
	private boolean status;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public RegistrationResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	public RegistrationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RegistrationResponse [message=" + message + ", status=" + status + "]";
	}
	
	
	
	
}
