package com.example.deveki.nimbusjosesignverifyexample;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.deveki.nimbusjosesignverifyexample.model.JWSSignInput;
import com.example.deveki.nimbusjosesignverifyexample.model.JWSSignResponse;
import com.example.deveki.nimbusjosesignverifyexample.model.JWSVerifyRequest;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

@RestController
public class JWSController {
	
	@PostMapping("/jws/sign")
	public JWSSignResponse sign(@RequestBody JWSSignInput jwsSignInput){
		JWSSignResponse response = new JWSSignResponse();
		
		// Create HMAC signer
		JWSSigner signer = new MACSigner(Constants.SHARED_SACRET.getBytes());

		// Prepare JWS object with "Hello, world!" payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jwsSignInput.getPayload()));
		
		// Apply the HMAC
		try {
			jwsObject.sign(signer);
			String s = jwsObject.serialize();
			response.setToken(s);
		} catch (JOSEException e) {
			response.setErrorMsg("JOSEException during signing: "+e.getMessage());
		}
		return response;
	}
	
	@PostMapping("/jws/verify")
	public String verify(@RequestBody JWSVerifyRequest request) {
		String token = request.getToken();
		try {
			JWSObject  jwsObject = JWSObject.parse(token);
			JWSVerifier verifier = new MACVerifier(Constants.SHARED_SACRET.getBytes());
			boolean v = jwsObject.verify(verifier);
			if(v) {
				return "Token successfully verified.";
			}else {
				return "Token verification failed.";
			}
		} catch (ParseException e) {
			return "ParseException during verification: "+e.getMessage();
		} catch (JOSEException e) {
			return "JOSEException during verification:  "+e.getMessage();
		}
		
	} 
}
