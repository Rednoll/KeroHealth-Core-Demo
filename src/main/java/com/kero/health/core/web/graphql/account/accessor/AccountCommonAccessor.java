package com.kero.health.core.web.graphql.account.accessor;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.web.graphql.human.accessor.HumanCommonAccessor;

public class AccountCommonAccessor extends AccountRootAccessor {

	public AccountCommonAccessor(Account account) {
		super(account);
		
	}
	
	@Override
	public Human getOwner() {
		
		return new HumanCommonAccessor(this.getOwner());
	}
}
