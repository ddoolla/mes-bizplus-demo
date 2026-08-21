package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.routing.dto.RoutingUpdateDto;
import com.bizplus.mes.domain.routing.process.RoutingProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoutingUpdateService {

    private final RoutingService routingService;
    private final RoutingProcessService routingProcessService;

    @Transactional
    public void update(Long routingId, RoutingUpdateDto dto) {
        routingService.updateRouting(routingId, dto);
        routingProcessService.updateRoutingProcesses(dto.getRoutingProcesses());
    }
}
