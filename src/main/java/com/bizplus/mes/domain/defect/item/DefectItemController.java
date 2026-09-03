package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.defect.item.dto.DefectItemCreateDto;
import com.bizplus.mes.domain.defect.item.dto.DefectItemSearchDto;
import com.bizplus.mes.domain.defect.item.dto.DefectItemUpdateDto;
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
@RequestMapping("/defect-items")
@RequiredArgsConstructor
public class DefectItemController {

    private final DefectItemService defectItemService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('DEFECT_ITEM_READ')")
    @UserAction(menu = MenuCode.DEFECT_ITEM, type = ActionType.READ)
    public String viewList(Model model, DefectItemSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("defectTypes", commonCodeService.getCommonCodes(CodeGroupKey.DEFECT_TYPE));
        model.addAttribute("data", defectItemService.getDefectItems(dto, pageable));

        return "pages/defect-item/list";
    }

    @GetMapping("/modal/form/new")
    @PreAuthorize("hasAuthority('DEFECT_ITEM_READ')")
    public String viewNewFormModal(Model model) {
        model.addAttribute("defectTypes", commonCodeService.getCommonCodes(CodeGroupKey.DEFECT_TYPE));

        return "pages/defect-item/modal/form/new :: form";
    }

    @GetMapping("/{id}/modal/form/edit")
    @PreAuthorize("hasAuthority('DEFECT_ITEM_READ')")
    public String viewEditFormModal(Model model, @PathVariable Long id) {
        model.addAttribute("defectTypes", commonCodeService.getCommonCodes(CodeGroupKey.DEFECT_TYPE));
        model.addAttribute("defectItem", defectItemService.getDefectItem(id));

        return "pages/defect-item/modal/form/edit :: form";
    }

    @GetMapping("/check-code")
    @ResponseBody
    @PreAuthorize("hasAuthority('DEFECT_ITEM_READ')")
    public boolean checkDefectItemCode(@RequestParam(required = false) Long id,
                                       @RequestParam String code) {
        return defectItemService.checkDefectItemCode(id, code);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('DEFECT_ITEM_CREATE')")
    @UserAction(menu = MenuCode.DEFECT_ITEM, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createDefectItem(@Valid DefectItemCreateDto dto) {
        defectItemService.createDefectItem(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('DEFECT_ITEM_UPDATE')")
    @UserAction(menu = MenuCode.DEFECT_ITEM, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> updateDefectItem(@PathVariable Long id,
                                                              @Valid DefectItemUpdateDto dto) {
        defectItemService.updateDefectItem(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('DEFECT_ITEM_DELETE')")
    @UserAction(menu = MenuCode.DEFECT_ITEM, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteDefectItem(@RequestBody List<Long> ids) {
        defectItemService.deleteDefectItems(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
