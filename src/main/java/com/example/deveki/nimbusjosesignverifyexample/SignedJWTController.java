package com.example.deveki.nimbusjosesignverifyexample;

import java.text.ParseException;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.deveki.nimbusjosesignverifyexample.model.Data;
import com.example.deveki.nimbusjosesignverifyexample.model.JWTSignRequest;
import com.example.deveki.nimbusjosesignverifyexample.model.JWTSignResponse;
import com.example.deveki.nimbusjosesignverifyexample.model.JWTVerifyRequest;
import com.example.deveki.nimbusjosesignverifyexample.model.JWTVerifyResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
@RestController
public class SignedJWTController {
	@PostMapping("/signedjwt/sign")
	public JWTSignResponse sign(@RequestBody JWTSignRequest jwtSignInput){
		System.out.println("SignedJWTController.sign method.");
		System.out.println("Input Data : "+jwtSignInput.toString());
		
		JWTSignResponse response = new JWTSignResponse();
		JWSSigner signer =null;
		try {
			// Create HMAC signer
			signer = new MACSigner(Constants.SHARED_SACRET.getBytes());
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(jwtSignInput.getSubject())
				    .issuer(jwtSignInput.getIssuer())
				    .expirationTime(new Date(new Date().getTime() + jwtSignInput.getExpTime() * 1000))
				    .claim("city",jwtSignInput.getClaim_city())
				    .claim("company",jwtSignInput.getClaim_companyName())
				    .claim("department",jwtSignInput.getClaim_department())
				    .claim("salary",jwtSignInput.getClaim_salary())
				    .build(); 

			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			signedJWT.sign(signer);
			String s = signedJWT.serialize();
			response.setToken(s);
		} catch (JOSEException e) {
			response.setErrorMessage("JOSEException during signing: "+e.getMessage());
		}
		return response;
	}
	
	@PostMapping("/signedjwt/verify")
	public JWTVerifyResponse verify(@RequestBody JWTVerifyRequest request) {
		String token = request.getToken();
		System.out.println("SignedJWTController.verify method.");
		System.out.println("Input Data : "+request.toString());
		JWTVerifyResponse response = new JWTVerifyResponse();
		try {
			SignedJWT   signedJWT = SignedJWT.parse(token);
			JWSVerifier  verifier = new MACVerifier(Constants.SHARED_SACRET.getBytes());
			boolean v = signedJWT.verify(verifier);
			if(v) {
				response.setStatus("Verified");
				JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
				Data data = new Data();
				data.setCity((String)claimSet.getClaim("city"));
				data.setCompanyName((String)claimSet.getClaim("company"));
				data.setDepartment((String)claimSet.getClaim("department"));
				data.setSalary((Long)claimSet.getClaim("salary"));
				response.setData(data);
			}else {
				response.setStatus("Not Verified");
			}
		} catch (ParseException e) {
			response.setErrorMessage("ParseException during verification: "+e.getMessage());
		} catch (JOSEException e) {
			response.setErrorMessage("JOSEException during verification:  "+e.getMessage());
		}
		return response;
	} 
}
