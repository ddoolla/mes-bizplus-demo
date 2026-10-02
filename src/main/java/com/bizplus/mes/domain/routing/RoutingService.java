package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.routing.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoutingService {

    RoutingListDto getRoutings(RoutingSearchDto dto, Pageable pageable);

    List<RoutingDto> getRoutings(Long itemId);

    RoutingDto getRouting(Long id);

    boolean checkRoutingCode(Long id, String code);

    Long createRouting(RoutingCreateDto dto);

    void updateRouting(Long id, RoutingUpdateDto dto);

    void deleteRoutings(List<Long> ids);
}
