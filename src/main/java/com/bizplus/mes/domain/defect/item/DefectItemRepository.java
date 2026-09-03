package com.bizplus.mes.domain.defect.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectItemRepository extends JpaRepository<DefectItem, Long>, DefectItemQueryRepository {

    Optional<DefectItem> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
