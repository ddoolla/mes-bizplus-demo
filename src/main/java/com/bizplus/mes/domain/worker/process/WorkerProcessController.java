package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.worker.process.dto.WorkerProcessCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WorkerProcessController {

    private final WorkerProcessService workerProcessService;
    private final MessageService messageService;

    @PostMapping("/workers/{workerId}/processes")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createWorkerProcesses(@PathVariable Long workerId,
                                                                   @RequestBody @Valid WorkerProcessCreateDto dto) {
        workerProcessService.createWorkerProcesses(workerId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping("/workers/processes")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteWorkerProcesses(@RequestBody List<Long> ids) {
        workerProcessService.deleteWorkerProcesses(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
