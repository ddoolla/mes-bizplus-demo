package com.bizplus.mes.domain.inspection.spec;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionSpecRepository extends JpaRepository<InspectionSpec, Long>, InspectionSpecQueryRepository {

    Optional<InspectionSpec> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
