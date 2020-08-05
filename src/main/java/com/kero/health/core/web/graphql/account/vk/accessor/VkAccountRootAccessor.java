package com.kero.health.core.web.graphql.account.vk.accessor;

import com.kero.health.core.domain.account.impl.VkAccount;
import com.kero.health.core.web.graphql.account.accessor.AccountRootAccessor;

public class VkAccountRootAccessor extends AccountRootAccessor implements VkAccount {

	protected VkAccount account;
	
	public VkAccountRootAccessor(VkAccount account) {
		super(account);
		
		this.account = account;
	}
	
	@Override
	public Long getUid() {
		
		return account.getUid();
	}
}
