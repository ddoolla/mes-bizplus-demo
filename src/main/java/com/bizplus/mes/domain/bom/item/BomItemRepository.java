package com.bizplus.mes.domain.bom.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BomItemRepository extends JpaRepository<BomItem, Long>, BomItemQueryRepository {

    boolean existsByBomIdAndItemId(Long bomId, Long itemId);
}
