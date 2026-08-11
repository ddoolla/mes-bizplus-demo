package com.bizplus.mes.domain.uom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UomRepository extends JpaRepository<Uom, Long>, UomQueryRepository {

    Optional<Uom> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
