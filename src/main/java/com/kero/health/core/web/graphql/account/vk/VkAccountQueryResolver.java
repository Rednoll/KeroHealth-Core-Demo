package com.kero.health.core.web.graphql.account.vk;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.impl.VkAccount;
import com.kero.health.core.web.graphql.account.vk.accessor.VkAccountCommonAccessor;
import com.kero.health.core.web.graphql.account.vk.accessor.VkAccountRootAccessor;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class VkAccountQueryResolver implements GraphQLQueryResolver {

	@Transactional
	public VkAccount vkAccount(Long id, String tokenType, String token) {

		VkAccount account = VkAccount.findById(id);
		
		if(tokenType != null && token != null) {
			
			AccessToken tokenObject = AccessToken.fromString(tokenType, token);
			
			return new VkAccountRootAccessor(account);

		}
		else {
			
			return new VkAccountCommonAccessor(account);
		}	
	}
}
