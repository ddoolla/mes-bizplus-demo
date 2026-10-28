package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.shipment.facade.ShipmentCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentApiController {

    private final MessageService messageService;
    private final ShipmentService shipmentService;

    private final ShipmentCompleteService shipmentCompleteService;

    @DeleteMapping
    @PreAuthorize("hasAuthority('SHIPMENT_DELETE')")
    @UserAction(menu = MenuCode.SHIPMENT, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteAll(@RequestBody List<Long> ids) {
        shipmentService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('SHIPMENT_UPDATE')")
    public ResponseEntity<ApiResponse<Void>> complete(@PathVariable Long id) {
        shipmentCompleteService.complete(id);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.COMPLETED)));
    }
}
