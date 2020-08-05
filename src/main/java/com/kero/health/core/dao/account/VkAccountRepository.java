package com.kero.health.core.dao.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kero.health.core.domain.account.impl.VkAccountImpl;

@Repository
public interface VkAccountRepository extends JpaRepository<VkAccountImpl, Long>{

}
