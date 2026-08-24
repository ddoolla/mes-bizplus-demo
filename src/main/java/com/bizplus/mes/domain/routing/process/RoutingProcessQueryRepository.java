package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.domain.routing.process.dto.RoutingProcessDto;

import java.util.List;

public interface RoutingProcessQueryRepository {

    List<RoutingProcessDto> findRoutingProcesses(Long routingId);

    RoutingProcessDto findRoutingProcess(Long id);
}
