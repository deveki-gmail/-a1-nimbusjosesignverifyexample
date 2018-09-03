package com.example.deveki.nimbusjosesignverifyexample.model;

public class JWTSignRequest {
	private String subject;
	private String issuer;
	private int expTime;
	//
	private String claim_companyName;
	private String claim_department;
	private int claim_salary;
	private String claim_city;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public int getExpTime() {
		return expTime;
	}
	public void setExpTime(int expTime) {
		this.expTime = expTime;
	}
	public String getClaim_companyName() {
		return claim_companyName;
	}
	public void setClaim_companyName(String claim_companyName) {
		this.claim_companyName = claim_companyName;
	}
	public String getClaim_department() {
		return claim_department;
	}
	public void setClaim_department(String claim_department) {
		this.claim_department = claim_department;
	}
	public int getClaim_salary() {
		return claim_salary;
	}
	public void setClaim_salary(int claim_salary) {
		this.claim_salary = claim_salary;
	}
	public String getClaim_city() {
		return claim_city;
	}
	public void setClaim_city(String claim_city) {
		this.claim_city = claim_city;
	}
	@Override
	public String toString() {
		return "JWTSignRequest [subject=" + subject + ", issuer=" + issuer + ", expTime=" + expTime
				+ ", claim_companyName=" + claim_companyName + ", claim_department=" + claim_department
				+ ", claim_salary=" + claim_salary + ", claim_city=" + claim_city + "]";
	}
	
	
}
