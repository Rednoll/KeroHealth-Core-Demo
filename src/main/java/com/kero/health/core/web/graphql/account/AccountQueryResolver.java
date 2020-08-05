package com.kero.health.core.web.graphql.account;

import org.springframework.stereotype.Component;

import com.kero.health.core.domain.account.Account;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.web.graphql.account.accessor.AccountAccessor;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class AccountQueryResolver implements GraphQLQueryResolver {

	public Account account(Long id, String tokenType, String token) {
		
		if(tokenType != null && token != null) {
			
			AccessToken tokenObject = AccessToken.fromString(tokenType, token);
			
			Account account = tokenObject.getAccount();
			
			return AccountAccessor.createRootAccessor(account);
		}
		else {
			
			Account account = Account.findById(id);
			
			return AccountAccessor.createCommonAccessor(account);
		}	
	}
}