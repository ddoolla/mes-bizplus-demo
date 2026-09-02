package com.bizplus.mes.domain.inspection.spec.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionSpecItemRepository extends
        JpaRepository<InspectionSpecItem, Long>, InspectionSpecItemQueryRepository {

    Optional<InspectionSpecItem> findByIdAndDeletedAtIsNull(Long id);
}
