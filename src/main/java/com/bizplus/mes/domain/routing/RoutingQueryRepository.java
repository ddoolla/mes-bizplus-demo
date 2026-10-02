package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.routing.dto.RoutingDto;
import com.bizplus.mes.domain.routing.dto.RoutingSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoutingQueryRepository {

    Page<RoutingDto> findRoutings(RoutingSearchDto dto, Pageable pageable);

    List<RoutingDto> findRoutings(Long itemId);

    Optional<RoutingDto> findRouting(Long id);
}
