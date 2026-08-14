package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.Bom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BomItemRepository extends JpaRepository<BomItem, Long>, BomItemQueryRepository {
    Long bom(Bom bom);
}
