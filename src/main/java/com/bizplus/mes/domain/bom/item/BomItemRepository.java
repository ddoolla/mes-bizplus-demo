package com.bizplus.mes.domain.bom.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BomItemRepository extends JpaRepository<BomItem, Long>, BomItemQueryRepository {

    Optional<BomItem> findByIdAndDeletedAtIsNull(Long id);

    List<BomItem> findByBomId(Long bomId);
}
