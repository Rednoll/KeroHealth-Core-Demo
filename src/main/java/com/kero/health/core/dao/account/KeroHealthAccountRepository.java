package com.kero.health.core.dao.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kero.health.core.domain.account.impl.KeroHealthAccountImpl;

@Repository
public interface KeroHealthAccountRepository extends JpaRepository<KeroHealthAccountImpl, Long> {

	public boolean existsByLogin(String login);
	public KeroHealthAccountImpl findByLoginOrOwnerContactsEmail(String login, String ownerEmail);
}
