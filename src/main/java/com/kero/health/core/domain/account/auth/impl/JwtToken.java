package com.kero.health.core.domain.account.auth.impl;

import java.time.Instant;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.VerifyResult;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenAccountNotFoundException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenExpiredException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenSignatureInvalidException;
import com.kero.health.core.domain.account.auth.exceptions.AccessTokenStructureInvalidException;

public class JwtToken extends AccessTokenBase {

	private static String SALT = "isozhdjf;ohO:ASHDOuhOA:HSdipASHd(A*SY)d6y7832gdoAGO&*SgASodg^AWTYO47q7(GAD8oAUGDSo8aGSdiAGSidladsbailh7123yl";
	private static ObjectMapper jsonMapper = new ObjectMapper();
	
	public JwtToken() {}
	
	public JwtToken(Account account, Instant expirationTime) {
		super(account, expirationTime);
	}
	
	public String toStringForm() {
	
		try {
		
			StringBuilder result = new StringBuilder();
			
			ObjectNode root = asJson();
		
			String header = jsonMapper.writeValueAsString(root.get("header"));
			String payload = jsonMapper.writeValueAsString(root.get("payload"));
			String signature = root.get("signature").asText();
			
			result.append(Base64.getEncoder().encodeToString(header.getBytes()) + ".");
			result.append(Base64.getEncoder().encodeToString(payload.getBytes()) + ".");
			result.append(signature);
			
			return result.toString();
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}

	public ObjectNode asJson() {
		
		try {
			
			ObjectNode token = jsonMapper.createObjectNode();
				
				ObjectNode header = jsonMapper.createObjectNode();
					header.put("alg", "SHA256");
					header.put("typ", "JWT");
					
				token.set("header", header);
				
				ObjectNode payload = jsonMapper.createObjectNode();
					payload.put("exp", this.expirationTime.getEpochSecond());
					payload.put("accountId", this.account.getId());
					
				token.set("payload", payload);
			
			String signatureText = jsonMapper.writeValueAsString(token.get("header"));
				signatureText += jsonMapper.writeValueAsString(token.get("payload"));
				signatureText += SALT;
				
			token.put("signature", DigestUtils.sha256Hex(signatureText.getBytes()));
			
			return token;
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	public static VerifyResult verify(String data) throws AccessTokenStructureInvalidException {
		
		boolean expired = false;
		boolean signatureValid = true;
		
		String[] parts = data.split("\\.");
		
		String rawHeader = parts[0];
		String rawPayload = parts[1];
		String signature = parts[2];
		
		String decodedHeader = new String(Base64.getDecoder().decode(rawHeader));
		String decodedPayload = new String(Base64.getDecoder().decode(rawPayload));
		
		ObjectNode header = null;
		ObjectNode payload = null;
	
		String signatureText = null;
		
		try {
			
			header = (ObjectNode) jsonMapper.readTree(decodedHeader);
			payload = (ObjectNode) jsonMapper.readTree(decodedPayload);
			
			signatureText = jsonMapper.writeValueAsString(header);
				signatureText += jsonMapper.writeValueAsString(payload);
				signatureText += SALT;
		}
		catch(Exception e) {
			
			throw new AccessTokenStructureInvalidException();
		}
		
		if(!DigestUtils.sha256Hex(signatureText.getBytes()).equals(signature)) {
			
			signatureValid = false;
		}

		if(Instant.now().getEpochSecond() > payload.get("exp").asLong()) {
			
			expired = true;
		}
		
		return new VerifyResult(signatureValid, expired);
	}
	
	public static JwtToken fromString(String data) {
		
		String[] parts = data.split("\\.");
		
		if(parts.length != 3) throw new AccessTokenStructureInvalidException();
		
		String rawHeader = parts[0];
		String rawPayload = parts[1];
		String signature = parts[2];
		
		String decodedHeader = null;
		String decodedPayload = null;
		
		try {
			
			decodedHeader = new String(Base64.getDecoder().decode(rawHeader));
			decodedPayload = new String(Base64.getDecoder().decode(rawPayload));
		}
		catch(IllegalArgumentException e) {
			
			throw new AccessTokenStructureInvalidException();
		}
		
		ObjectNode header = null;
		ObjectNode payload = null;
	
		String signatureText = null;
		
		try {
			
			header = (ObjectNode) jsonMapper.readTree(decodedHeader);
			payload = (ObjectNode) jsonMapper.readTree(decodedPayload);
			
			signatureText = jsonMapper.writeValueAsString(header);
				signatureText += jsonMapper.writeValueAsString(payload);
				signatureText += SALT;
		}
		catch(Exception e) {
			
			throw new AccessTokenStructureInvalidException();
		}
		
		if(!DigestUtils.sha256Hex(signatureText.getBytes()).equals(signature)) throw new AccessTokenSignatureInvalidException();
	
		Account account = null;
		Instant expirationTime = null;
		
		try {
			
			account = Account.findById(payload.get("accountId").asLong());
			expirationTime = Instant.ofEpochSecond(payload.get("exp").asLong());
		}
		catch(Exception e) {
			
			throw new AccessTokenStructureInvalidException();
		}
		
		if(account == null) throw new AccessTokenAccountNotFoundException();
		
		if(Instant.now().getEpochSecond() > expirationTime.getEpochSecond()) {
			
			throw new AccessTokenExpiredException();
		}
		
		return new JwtToken(account, expirationTime);
	}
}
