package com.kero.health.core.domain.account.impl;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.kero.health.core.dao.account.VkAccountRepository;

@Entity(name = "VkAccount")
public class VkAccountImpl extends AccountBase implements VkAccount {

	private static VkAccountRepository repository;
	
	private Long uid;
	
	@Override
	public Long getUid() {
		
		return this.uid;
	}
	
	@Override
	public String getType() {
		
		return "VK";
	}
	
	public static VkAccountImpl findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	@Service
	private class RepositoryInjector {
		
		@Autowired
		private VkAccountRepository rep;
	
		@PostMapping
		private void inject() {
			
			repository = rep;
		}
	}
}
