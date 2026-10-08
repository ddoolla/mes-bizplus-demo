package com.bizplus.mes.domain.lot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long>, LotQueryRepository {

    Optional<Lot> findByIdAndDeletedAtIsNull(Long id);
}
