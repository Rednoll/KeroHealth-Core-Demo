package com.kero.health.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kero.health.core.domain.impl.HumanImpl;

@Repository
public interface HumanRepository extends JpaRepository<HumanImpl, Long> {

	public boolean existsByContactsEmail(String email);
}
