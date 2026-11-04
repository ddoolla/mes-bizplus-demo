package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessStepDto;

import java.util.List;

public interface RoutingProcessService {

    List<RoutingProcessDto> getRoutingProcesses(Long routingId);

    RoutingProcessDto getRoutingProcess(Long id);

    void createRoutingProcesses(Long routingId, RoutingProcessCreateDto dto);

    void updateSteps(List<RoutingProcessStepDto> dtos);

    void deleteRoutingProcesses(List<Long> ids);
}
