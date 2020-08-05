package com.kero.health.core.web.graphql.human.accessor;

import java.util.HashSet;
import java.util.Set;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.life.Life;
import com.kero.health.core.web.graphql.account.accessor.AccountAccessor;

public class HumanCommonAccessor extends HumanRootAccessor {

	public HumanCommonAccessor() {}
	
	public HumanCommonAccessor(Human human) {
		super(human);
	}
	
	@Override
	public Life getLife() {
		
		return null;
	}

	@Override
	public Set<Account> getAccounts() {

		Set<Account> accounts = new HashSet<>();
		
		for(Account account : human.getAccounts()){
			
			accounts.add(AccountAccessor.createCommonAccessor(account));
		}
		
		return accounts;
	}
}
