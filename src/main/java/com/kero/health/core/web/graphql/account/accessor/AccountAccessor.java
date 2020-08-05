package com.kero.health.core.web.graphql.account.accessor;

import org.hibernate.Hibernate;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;
import com.kero.health.core.domain.account.impl.VkAccount;
import com.kero.health.core.web.graphql.account.kerohealth.accessor.KeroHealthAccountCommonAccessor;
import com.kero.health.core.web.graphql.account.kerohealth.accessor.KeroHealthAccountRootAccessor;
import com.kero.health.core.web.graphql.account.vk.accessor.VkAccountCommonAccessor;
import com.kero.health.core.web.graphql.account.vk.accessor.VkAccountRootAccessor;

public interface AccountAccessor extends Account {

	public static AccountAccessor createRootAccessor(Account account) {
		
		account = (Account) Hibernate.unproxy(account); //TODO: PERFOMANCE
		
		if(account instanceof VkAccount) {
			
			return new VkAccountRootAccessor((VkAccount) account);
		}
		else if(account instanceof KeroHealthAccount) {
			
			return new KeroHealthAccountRootAccessor((KeroHealthAccount) account);
		}
		
		return null;
	}
	
	public static AccountAccessor createCommonAccessor(Account account) {
		
		account = (Account) Hibernate.unproxy(account); //TODO: PERFOMANCE
		
		if(account instanceof VkAccount) {
			
			return new VkAccountCommonAccessor((VkAccount) account);
		}
		else if(account instanceof KeroHealthAccount) {
			
			return new KeroHealthAccountCommonAccessor((KeroHealthAccount) account);
		}
		
		return null;
	}
}
