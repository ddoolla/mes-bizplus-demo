package com.bizplus.mes.domain.bom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BomRepository extends JpaRepository<Bom, Long>, BomQueryRepository {

    Optional<Bom> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
