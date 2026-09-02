package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemCreateDto;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemSearchDto;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemUpdateDto;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inspection-items")
@RequiredArgsConstructor
public class InspectionItemController {

    private final InspectionItemService inspectionItemService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_READ')")
    @UserAction(menu = MenuCode.INSPECTION_ITEM, type = ActionType.READ)
    public String viewList(Model model,
                           InspectionItemSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("inspectionGroups", commonCodeService.getCommonCodes(CodeGroupKey.INSPECTION_GROUP));
        model.addAttribute("data", inspectionItemService.getInspectionItems(dto, pageable));

        return "pages/inspection-item/list";
    }

    @GetMapping("/modal/form/new")
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_READ')")
    public String viewNewFormModal(Model model) {
        model.addAttribute("inspectionGroups", commonCodeService.getCommonCodes(CodeGroupKey.INSPECTION_GROUP));

        return "pages/inspection-item/modal/form/new :: form";
    }

    @GetMapping("/{id}/modal/form/edit")
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_READ')")
    public String viewEditFormModal(@PathVariable Long id, Model model) {
        model.addAttribute("inspectionGroups", commonCodeService.getCommonCodes(CodeGroupKey.INSPECTION_GROUP));
        model.addAttribute("inspectionItem", inspectionItemService.getInspectionItem(id));

        return "pages/inspection-item/modal/form/edit :: form";
    }

    @GetMapping("/modal/list/multiple")
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_READ')")
    public String viewMultipleListModal(Model model,
                                        InspectionItemSearchDto dto,
                                        @PageableDefault Pageable pageable) {
        model.addAttribute("data", inspectionItemService.getInspectionItems(dto, pageable));

        return "pages/inspection-item/modal/list/multiple :: list";
    }

    @GetMapping("/check-code")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_READ')")
    public boolean checkInspectionCode(@RequestParam(required = false) Long id,
                                       @RequestParam String code) {
        return inspectionItemService.checkInspectionItemCode(id, code);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_CREATE')")
    @UserAction(menu = MenuCode.INSPECTION_ITEM, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createInspectionCode(@Valid InspectionItemCreateDto dto) {
        inspectionItemService.createInspectionItem(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_UPDATE')")
    @UserAction(menu = MenuCode.INSPECTION_ITEM, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> updateInspectionCode(@PathVariable Long id,
                                                                  @Valid InspectionItemUpdateDto dto) {
        inspectionItemService.updateInspectionItem(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_ITEM_DELETE')")
    @UserAction(menu = MenuCode.INSPECTION_ITEM, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteInspectionCode(@RequestBody List<Long> ids) {
        inspectionItemService.deleteInspectionItems(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
