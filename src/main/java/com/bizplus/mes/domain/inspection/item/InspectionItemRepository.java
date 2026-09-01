package com.bizplus.mes.domain.inspection.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, Long>, InspectionItemQueryRepository {

    Optional<InspectionItem> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
