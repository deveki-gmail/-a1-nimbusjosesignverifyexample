package com.example.deveki.nimbusjosesignverifyexample.model;

public class JWTVerifyRequest {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JWTVerifyRequest [token=" + token + "]";
	}
	
	
}
