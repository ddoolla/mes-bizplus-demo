package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShipmentItemApiController {

    private final MessageService messageService;
    private final ShipmentItemService shipmentItemService;

    @PostMapping("/shipments/{shipmentId}/items")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long shipmentId,
                                                    @RequestBody ShipmentItemCreateDto dto) {
        shipmentItemService.createAll(shipmentId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }
}
