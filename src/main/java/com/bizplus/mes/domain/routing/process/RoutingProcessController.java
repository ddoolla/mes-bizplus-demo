package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoutingProcessController {

    private final RoutingProcessService routingProcessService;
    private final MessageService messageService;

    @PostMapping("/routings/{routingId}/processes")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createRoutingProcesses(@PathVariable Long routingId,
                                                                    @RequestBody @Valid RoutingProcessCreateDto dto) {
        routingProcessService.createRoutingProcesses(routingId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping("/routing-processes")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteRoutingProcesses(@RequestBody List<Long> ids) {
        routingProcessService.deleteRoutingProcesses(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
