package com.kero.health.core.web.graphql.account.vk.accessor;

import com.kero.health.core.domain.account.impl.VkAccount;
import com.kero.health.core.web.graphql.account.accessor.AccountCommonAccessor;

public class VkAccountCommonAccessor extends AccountCommonAccessor implements VkAccount {

	public VkAccountCommonAccessor(VkAccount account) {
		super(account);
		
	}
	
	@Override
	public Long getUid() {
		
		return null;
	}
	
	@Override
	public Long getId() {
		
		return null;
	}
}
