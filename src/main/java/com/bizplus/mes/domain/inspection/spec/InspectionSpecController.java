package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecCreateDto;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecSearchDto;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecUpdateDto;
import com.bizplus.mes.domain.item.ItemType;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/inspection-specs")
@RequiredArgsConstructor
public class InspectionSpecController {

    private final InspectionSpecService inspectionSpecService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_READ')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.READ)
    public String viewList(Model model, InspectionSpecSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("inspectionTypes", InspectionType.values());
        model.addAttribute("data", inspectionSpecService.getInspectionSpecs(dto, pageable));

        return "pages/inspection-spec/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("inspectionSpec", inspectionSpecService.getInspectionSpec(id));

        return "pages/inspection-spec/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_READ')")
    public String viewNew(Model model) {
        model.addAttribute("inspectionTypes", InspectionType.values());
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());

        return "pages/inspection-spec/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("inspectionTypes", InspectionType.values());
        model.addAttribute("inspectionSpec", inspectionSpecService.getInspectionSpec(id));

        return "pages/inspection-spec/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_READ')")
    public boolean checkInspectionSpecCode(@RequestParam(required = false) Long id,
                                           @RequestParam String code) {
        return inspectionSpecService.checkInspectionSpecCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_CREATE')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.CREATE)
    public String createInspectionSpec(@Valid InspectionSpecCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = inspectionSpecService.createInspectionSpec(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/inspection-specs/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_UPDATE')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.UPDATE)
    public String updateInspectionSpec(@PathVariable Long id,
                                       @Valid InspectionSpecUpdateDto dto,
                                       RedirectAttributes reAtt) {
        inspectionSpecService.updateInspectionSpec(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/inspection-specs/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('INSPECTION_SPEC_DELETE')")
    @UserAction(menu = MenuCode.INSPECTION_SPEC, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteInspectionSpecs(@RequestBody List<Long> ids) {
        inspectionSpecService.deleteInspectionSpecs(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
