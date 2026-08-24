package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialBomCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialItemCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProcessMaterialController {

    private final ProcessMaterialService processMaterialService;
    private final MessageService messageService;

    @PostMapping("/routing-processes/{routingProcessId}/materials/from-items")
    private ResponseEntity<ApiResponse<Void>> createProcessMaterialsByItem(@PathVariable Long routingProcessId,
                                                                           @RequestBody @Valid ProcessMaterialItemCreateDto dto) {
        processMaterialService.createProcessMaterialsByItem(routingProcessId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PostMapping("/routing-processes/{routingProcessId}/materials/from-boms")
    private ResponseEntity<ApiResponse<Void>> createProcessMaterialsByBom(@PathVariable Long routingProcessId,
                                                                          @RequestBody @Valid ProcessMaterialBomCreateDto dto) {
        processMaterialService.createProcessMaterialsByBom(routingProcessId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PostMapping("/routings/{routingId}/processes/{routingProcessId}/materials")
    public String updateProcessMaterials(RedirectAttributes reAtt,
                                         @PathVariable Long routingId,
                                         @PathVariable Long routingProcessId,
                                         @NotEmpty List<ProcessMaterialUpdateDto> dtos) {
        processMaterialService.updateProcessMaterials(dtos);

        reAtt.addAttribute("routingId", routingId);
        reAtt.addAttribute("routingProcessId", routingProcessId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/routings/{routingId}/processes/{routingProcessId}";
    }

    @DeleteMapping("/process-materials")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteProcessMaterials(@RequestBody List<Long> ids) {
        processMaterialService.deleteProcessMaterials(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
