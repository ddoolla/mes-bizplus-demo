package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingProcessReader {

    private final RoutingProcessRepository routingProcessRepository;

    public RoutingProcess getById(Long id) {
        return routingProcessRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTING_PROCESS_NOT_FOUND, "id: " + id));
    }
}
