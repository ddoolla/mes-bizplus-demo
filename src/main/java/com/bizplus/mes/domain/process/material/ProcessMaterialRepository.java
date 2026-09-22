package com.bizplus.mes.domain.process.material;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessMaterialRepository extends
        JpaRepository<ProcessMaterial, Long>, ProcessMaterialQueryRepository {

    boolean existsByRoutingProcessIdAndItemId(Long routingProcessId, Long itemId);
}
