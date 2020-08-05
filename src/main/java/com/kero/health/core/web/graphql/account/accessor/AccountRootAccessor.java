package com.kero.health.core.web.graphql.account.accessor;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.web.graphql.human.accessor.HumanRootAccessor;

public class AccountRootAccessor implements AccountAccessor {

	protected Account account;
	
	public AccountRootAccessor(Account account) {
	
		this.account = account;
	}
	
	@Override
	public Human getOwner() {
		
		return new HumanRootAccessor(this.getOwner());
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
		
	}
}