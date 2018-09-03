package com.example.deveki.nimbusjosesignverifyexample;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.deveki.nimbusjosesignverifyexample.model.JWSSignInput;
import com.example.deveki.nimbusjosesignverifyexample.model.JWSSignResponse;
import com.example.deveki.nimbusjosesignverifyexample.model.JWSVerifyRequest;
import com.example.deveki.nimbusjosesignverifyexample.model.JWSVerifyResponse;
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
		System.out.println("JWSController.sign method. Payload = "+jwsSignInput.getPayload());
		try {
			// Create HMAC signer
			JWSSigner signer = new MACSigner(Constants.SHARED_SACRET.getBytes());

			// Prepare JWS object with "Hello, world!" payload
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jwsSignInput.getPayload()));
			jwsObject.sign(signer);
			String s = jwsObject.serialize();
			response.setToken(s);
		} catch (JOSEException e) {
			response.setErrorMsg("JOSEException during signing: "+e.getMessage());
		}
		return response;
	}
	
	@PostMapping("/jws/verify")
	public JWSVerifyResponse verify(@RequestBody JWSVerifyRequest request) {
		String token = request.getToken();
		System.out.println("JWSController.verify method. Token = "+request.getToken());
		JWSVerifyResponse response = new JWSVerifyResponse();
		try {
			JWSObject  jwsObject = JWSObject.parse(token);
			JWSVerifier verifier = new MACVerifier(Constants.SHARED_SACRET.getBytes());
			boolean v = jwsObject.verify(verifier);
			if(v) {
				response.setStatus("Verified");
				response.setPayload(jwsObject.getPayload().toString());
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
