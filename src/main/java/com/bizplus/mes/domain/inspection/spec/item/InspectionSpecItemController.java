package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemCreateDto;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InspectionSpecItemController {

    private final InspectionSpecItemService inspectionSpecItemService;
    private final MessageService messageService;

    @PostMapping("/inspection-specs/{specId}/items")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_CREATE')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createInspectionSpecItems(@PathVariable Long specId,
                                                                       @RequestBody @Valid InspectionSpecItemCreateDto dto) {
        inspectionSpecItemService.createInspectionSpecItems(specId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping("/inspection-spec-items")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_DELETE')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteInspectionSpecItems(@RequestBody List<Long> ids) {
        inspectionSpecItemService.deleteInspectionSpecItems(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
