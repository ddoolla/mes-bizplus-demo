package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.routing.dto.RoutingCreateDto;
import com.bizplus.mes.domain.routing.dto.RoutingUpdateDto;

public class RoutingMapper {

    public static Routing toEntity(Item item, RoutingCreateDto dto) {
        return new Routing(
                item,
                dto.getCode(),
                dto.getName(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getDescription()
        );
    }

    public static void apply(Routing routing, RoutingUpdateDto dto) {
        routing.update(
                dto.getCode(),
                dto.getName(),
                dto.getVersion(),
                dto.isPrimary(),
                dto.getDescription()
        );
    }
}
