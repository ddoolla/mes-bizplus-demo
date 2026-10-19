package com.bizplus.mes.domain.process.material;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessMaterialRepository extends
        JpaRepository<ProcessMaterial, Long>, ProcessMaterialQueryRepository {

    List<ProcessMaterial> findByRoutingProcessId(Long routingProcessId);

    boolean existsByRoutingProcessIdAndItemId(Long routingProcessId, Long itemId);
}
