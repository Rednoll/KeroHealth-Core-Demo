package com.kero.health.core.web.graphql.account.kerohealth.accessor;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;
import com.kero.health.core.web.graphql.account.accessor.AccountAccessor;

public class KeroHealthAccountRootAccessor implements KeroHealthAccount, AccountAccessor {

	protected KeroHealthAccount account;
	
	public KeroHealthAccountRootAccessor(KeroHealthAccount account) {
		
		this.account = account;
	}
	
	@Override
	public String getLogin() {
		
		return account.getLogin();
	}
	
	@Override
	public byte[] getPassHash() {
		
		return null;
	}
	
	@Override
	public Human getOwner() {
		
		return account.getOwner();
	}

	@Override
	public Long getId() {
		
		return account.getId();
	}

	@Override
	public String getType() {
		
		return account.getType();
	}

	@Override
	public Account save() {
		
		return null;
	}

	@Override
	public void setOwner(Human owner) {
		// TODO Auto-generated method stub
		
	}
}