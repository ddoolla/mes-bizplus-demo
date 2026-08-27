package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.uom.UomService;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionCreateDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionSearchDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionUpdateDto;
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
@RequestMapping("/uoms/conversions")
@RequiredArgsConstructor
public class UomConversionController {

    private final UomConversionService uomConversionService;
    private final UomService uomService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('UOM_READ')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.READ)
    public String viewList(Model model,
                           UomConversionSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("data", uomConversionService.getUomConversions(dto, pageable));
        model.addAttribute("uoms", uomService.getUoms());

        return "pages/uom-conversion/list";
    }

    @GetMapping("/modal/create-content")
    @PreAuthorize("hasAuthority('UOM_READ')")
    public String viewModalCreateContent(Model model) {
        model.addAttribute("uoms", uomService.getUoms());

        return "pages/uom-conversion/modal/create-content :: content";
    }

    @GetMapping("/{id}/modal/edit-content")
    @PreAuthorize("hasAuthority('UOM_READ')")
    public String viewModalEditContent(Model model, @PathVariable Long id) {
        model.addAttribute("uomConversion", uomConversionService.getUomConversion(id));

        return "pages/uom-conversion/modal/edit-content :: content";
    }

    @GetMapping("/duplicate")
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_READ')")
    public boolean checkDuplication(@RequestParam(required = false) Long fromUomId,
                                    @RequestParam(required = false) Long toUomId) {
        return uomConversionService.checkDuplication(fromUomId, toUomId);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_CREATE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createUomConversion(@Valid UomConversionCreateDto dto) {
        uomConversionService.createUomConversion(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_UPDATE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> updateUomConversion(@PathVariable Long id,
                                                                 @Valid UomConversionUpdateDto dto) {
        uomConversionService.updateUomConversion(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_DELETE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteUomConversions(@RequestBody List<Long> ids) {
        uomConversionService.deleteUomConversions(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }

}
