package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessUpdateDto;

import java.util.List;

public interface RoutingProcessService {

    List<RoutingProcessDto> getRoutingProcesses(Long routingId);

    RoutingProcessDto getRoutingProcess(Long id);

    void createRoutingProcesses(Long routingId, RoutingProcessCreateDto dto);

    void updateRoutingProcesses(List<RoutingProcessUpdateDto> dtos);

    void deleteRoutingProcesses(List<Long> ids);
}
