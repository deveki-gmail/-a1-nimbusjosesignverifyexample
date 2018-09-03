package com.example.deveki.nimbusjosesignverifyexample.model;

public class JWTSignResponse {
	private String token;
	private String errorMessage;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
