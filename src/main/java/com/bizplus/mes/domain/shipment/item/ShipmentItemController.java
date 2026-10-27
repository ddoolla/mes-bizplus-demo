package com.bizplus.mes.domain.shipment.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ShipmentItemController {

    private final ShipmentItemService shipmentItemService;
}
