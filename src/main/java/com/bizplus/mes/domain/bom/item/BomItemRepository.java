package com.bizplus.mes.domain.bom.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BomItemRepository extends JpaRepository<BomItem, Long>, BomItemQueryRepository {

    List<BomItem> findByBomId(Long bomId);

    boolean existsByBomIdAndItemId(Long bomId, Long itemId);
}
