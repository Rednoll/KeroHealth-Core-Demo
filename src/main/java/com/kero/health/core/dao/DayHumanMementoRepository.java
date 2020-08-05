package com.kero.health.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kero.health.core.domain.impl.DayHumanMementoImpl;
import com.kero.health.core.domain.life.additionals.DayHumanMementoId;

public interface DayHumanMementoRepository extends JpaRepository<DayHumanMementoImpl, DayHumanMementoId> {

}
