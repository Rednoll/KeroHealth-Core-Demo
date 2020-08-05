package com.kero.health.core.web.graphql.account.kerohealth.accessor;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;

public class KeroHealthAccountCommonAccessor extends KeroHealthAccountRootAccessor {

	public KeroHealthAccountCommonAccessor(KeroHealthAccount account) {
		super(account);
	}
	
	@Override
	public Human getOwner() {
		
		return null;
	}

	@Override
	public Long getId() {
		
		return null;
	}
}
