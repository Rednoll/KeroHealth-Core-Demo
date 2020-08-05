package com.kero.health.core.web.graphql.human;

import org.springframework.stereotype.Component;

import com.kero.health.core.domain.Human;
import com.kero.health.core.domain.account.auth.AccessToken;
import com.kero.health.core.web.graphql.human.accessor.HumanCommonAccessor;
import com.kero.health.core.web.graphql.human.accessor.HumanRootAccessor;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class HumanQueryResolver implements GraphQLQueryResolver {
	
	public Human human(Long id, String tokenType, String token) {
		
		Human human = null;
		
		if(tokenType != null && token != null) {
			
			AccessToken tokenObj = AccessToken.fromString(tokenType, token);
			
			human = new HumanRootAccessor(tokenObj.getAccount().getOwner());

		}
		else if(id != null) {
			
			human = new HumanCommonAccessor(Human.findById(id));
		}

		return human;
	}
}
