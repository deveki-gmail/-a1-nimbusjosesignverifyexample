package com.example.deveki.nimbusjosesignverifyexample.model;

public class JWTVerifyResponse {
	
	private String status;
	
	private Data data;
	
	private String errorMessage;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
	
}
