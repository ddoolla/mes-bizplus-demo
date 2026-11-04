package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.bom.BomService;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import com.bizplus.mes.domain.process.material.ProcessMaterialService;
import com.bizplus.mes.domain.routing.RoutingService;
import com.bizplus.mes.domain.routing.dto.RoutingDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessUpdateDto;
import com.bizplus.mes.domain.routing.process.facade.RoutingProcessUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoutingProcessController {

    private final MessageService messageService;
    private final RoutingService routingService;
    private final RoutingProcessService routingProcessService;
    private final ProcessMaterialService processMaterialService;
    private final CommonCodeService commonCodeService;
    private final BomService bomService;

    private final RoutingProcessUpdateService routingProcessUpdateService;

    @GetMapping("/routings/{routingId}/processes/{id}")
    public String viewDetail(Model model,
                             @PathVariable Long routingId,
                             @PathVariable Long id) {
        RoutingDto routing = routingService.getRouting(routingId);
        ItemType itemType = routing.getItem().type();

        ItemGroup itemGroup = switch (itemType) {
            case FINISHED_GOODS -> ItemGroup.FINISHED_GOODS_BOM_ITEM;
            case SEMI_FINISHED_GOODS -> ItemGroup.SEMI_FINISHED_GOODS_BOM_ITEM;
            default -> throw new IllegalStateException("BOM을 구성할 수 없는 품목 유형입니다.");
        };

        // 소모자재 등록 모달
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemGroup", itemGroup);
        model.addAttribute("itemTypes", itemGroup.getTypes());
        model.addAttribute("boms", bomService.getBoms(routing.getItem().id()));

        // 공정 단계 정보
        model.addAttribute("routing", routing);
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

    @PostMapping("/routings/{routingId}/processes/{id}")
    public String update(@PathVariable Long routingId,
                         @PathVariable Long id,
                         @Valid RoutingProcessUpdateDto dto,
                         RedirectAttributes reAtt) {
        routingProcessUpdateService.update(id, dto);

        reAtt.addAttribute("routingId", routingId);
        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/routings/{routingId}/processes/{id}";
    }

    @DeleteMapping("/routing-processes")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteRoutingProcesses(@RequestBody List<Long> ids) {
        routingProcessService.deleteRoutingProcesses(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
