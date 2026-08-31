package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.equipment.dto.EquipmentCreateDto;
import com.bizplus.mes.domain.equipment.dto.EquipmentSearchDto;
import com.bizplus.mes.domain.equipment.dto.EquipmentUpdateDto;
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
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    @UserAction(menu = MenuCode.EQUIPMENT, type = ActionType.READ)
    public String viewList(Model model,
                           EquipmentSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("equipmentTypes", commonCodeService.getCommonCodes(CodeGroupKey.EQUIPMENT_TYPE));
        model.addAttribute("data", equipmentService.getEquipments(dto, pageable));

        return "pages/equipment/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("equipment", equipmentService.getEquipment(id));

        return "pages/equipment/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public String viewNew(Model model) {
        model.addAttribute("equipmentTypes", commonCodeService.getCommonCodes(CodeGroupKey.EQUIPMENT_TYPE));

        return "pages/equipment/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('EQUIPMENT_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("equipmentTypes", commonCodeService.getCommonCodes(CodeGroupKey.EQUIPMENT_TYPE));
        model.addAttribute("equipment", equipmentService.getEquipment(id));

        return "pages/equipment/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkEquipmentCode(@RequestParam(required = false) Long id,
                                      @RequestParam String code) {
        return equipmentService.checkEquipmentCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EQUIPMENT_CREATE')")
    @UserAction(menu = MenuCode.EQUIPMENT, type = ActionType.CREATE)
    public String createEquipment(@Valid EquipmentCreateDto dto, RedirectAttributes reAtt) {
        equipmentService.createEquipment(dto);

        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/equipments";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIPMENT_UPDATE')")
    @UserAction(menu = MenuCode.EQUIPMENT, type = ActionType.UPDATE)
    public String updateEquipment(@PathVariable Long id, @Valid EquipmentUpdateDto dto, RedirectAttributes reAtt) {
        equipmentService.updateEquipment(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/equipments/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('EQUIPMENT_DELETE')")
    @UserAction(menu = MenuCode.EQUIPMENT, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteEquipments(@RequestBody List<Long> ids) {
        equipmentService.deleteEquipments(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}