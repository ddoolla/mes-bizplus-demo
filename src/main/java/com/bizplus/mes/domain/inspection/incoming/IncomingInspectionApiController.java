package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.inspection.incoming.facade.IncomingInspectionCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incoming-inspections")
@RequiredArgsConstructor
public class IncomingInspectionApiController {

    private final MessageService messageService;
    private final IncomingInspectionService incomingInspectionService;
    private final IncomingInspectionCompleteService incomingInspectionCompleteService;

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> complete(@PathVariable Long id) {
        incomingInspectionCompleteService.complete(id);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.COMPLETED)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAll(@RequestBody List<Long> ids) {
        incomingInspectionService.deleteAll(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
