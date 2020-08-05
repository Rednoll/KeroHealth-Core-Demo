package com.kero.health.core.dao.account;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kero.health.core.domain.account.impl.AccountBase;

@Repository
@Primary
public interface AccountRepository extends JpaRepository<AccountBase, Long>{

}
