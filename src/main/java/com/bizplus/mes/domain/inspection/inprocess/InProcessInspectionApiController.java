package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.inspection.inprocess.facade.InProcessInspectionCompleteService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/in-process-inspections")
@RequiredArgsConstructor
public class InProcessInspectionApiController {

    private final MessageService messageService;
    private final InProcessInspectionService inProcessInspectionService;
    private final InProcessInspectionCompleteService inProcessInspectionCompleteService;

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> complete(@PathVariable Long id) {
        inProcessInspectionCompleteService.complete(id);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.COMPLETED))
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_DELETE')")
    @UserAction(menu = MenuCode.IN_PROCESS_INSPECTION, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteAll(@RequestBody List<Long> ids) {
        inProcessInspectionService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED))
        );
    }
}
