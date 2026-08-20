package com.bizplus.mes.domain.routing;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingReader {

    private final RoutingRepository routingRepository;

    public Routing getById(Long id) {
        return routingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTING_NOT_FOUND, "id: " + id));
    }
}
