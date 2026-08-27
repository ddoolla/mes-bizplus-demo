package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.bom.BomService;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import com.bizplus.mes.domain.process.material.ProcessMaterialService;
import com.bizplus.mes.domain.routing.RoutingService;
import com.bizplus.mes.domain.routing.dto.RoutingDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoutingProcessController {

    private final RoutingService routingService;
    private final RoutingProcessService routingProcessService;
    private final ProcessMaterialService processMaterialService;
    private final CommonCodeService commonCodeService;
    private final BomService bomService;
    private final MessageService messageService;

    @GetMapping("/routings/{routingId}/processes/{id}")
    public String viewDetail(Model model,
                             @PathVariable Long routingId,
                             @PathVariable Long id) {
        RoutingDto routing = routingService.getRouting(routingId);
        Long itemId = routing.getItem().id();

        // 소모자재 등록 모달
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemType", ItemGroup.BOM_ITEM.getTypes());
        model.addAttribute("boms", bomService.getBoms(itemId));
        model.addAttribute("primaryBom", bomService.getPrimaryBom(itemId).orElse(null));

        // 공정 단계 정보
        model.addAttribute("routing", routingService.getRouting(routingId));
        model.addAttribute("routingProcess", routingProcessService.getRoutingProcess(id));

        // 공정 소모 자재 목록
        model.addAttribute("consumptionMethods", ConsumptionMethod.values());
        model.addAttribute("processMaterialData", processMaterialService.getProcessMaterialsForEdit(id));

        return "pages/routing-process/detail";
    }

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
