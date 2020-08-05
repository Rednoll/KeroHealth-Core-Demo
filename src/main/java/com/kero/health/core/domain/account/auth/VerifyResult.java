package com.kero.health.core.domain.account.auth;

public class VerifyResult {

	private boolean signatureValid;
	private boolean expired;
	
	public VerifyResult() {}
	
	public VerifyResult(boolean signatureValid, boolean expired) {
		
		this.signatureValid = signatureValid;
		this.expired = expired;
	}
	
	public boolean isExpired() {
		
		return this.expired;
	}

	public boolean isSignatureValid() {
		
		return this.signatureValid;
	}
}
