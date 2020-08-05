package com.kero.health.core.web.graphql.account.kerohealth;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.domain.account.impl.KeroHealthAccount;
import com.kero.health.core.web.graphql.account.kerohealth.accessor.KeroHealthAccountCommonAccessor;
import com.kero.health.core.web.graphql.account.kerohealth.accessor.KeroHealthAccountRootAccessor;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class KeroHealthAccountQueryResolver implements GraphQLQueryResolver {

	@Transactional
	public KeroHealthAccount keroHealthAccount(Long id, String tokenType, String token) {

		if(tokenType != null && token != null) {
			
			AccessToken tokenObject = AccessToken.fromString(tokenType, token);
			
			KeroHealthAccount account = (KeroHealthAccount) Hibernate.unproxy(tokenObject.getAccount()); //TODO: PERFOMANCE WARNING

			return new KeroHealthAccountRootAccessor(account);

		}
		else {
			
			KeroHealthAccount account = KeroHealthAccount.findById(id);
			
			return new KeroHealthAccountCommonAccessor(account);
		}	
	}
}