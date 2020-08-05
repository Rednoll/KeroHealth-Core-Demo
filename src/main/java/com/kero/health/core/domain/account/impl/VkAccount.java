package com.kero.health.core.domain.account.impl;

import com.kero.health.core.domain.account.Account;

public interface VkAccount extends Account {

	public Long getUid();
	
	public static VkAccount findById(Long id) {
		
		return VkAccountImpl.findById(id);
	}
}
