package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.routing.dto.RoutingDto;
import com.bizplus.mes.domain.routing.dto.RoutingSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoutingQueryRepository {

    Page<RoutingDto> findRoutings(RoutingSearchDto dto, Pageable pageable);

    Optional<RoutingDto> findRouting(Long id);

    boolean existsPrimary(Long itemId);

    void resetPrimary(Long itemId);
}
