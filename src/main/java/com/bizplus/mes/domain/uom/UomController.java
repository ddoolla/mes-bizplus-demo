package com.bizplus.mes.domain.uom;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.uom.dto.UomCreateDto;
import com.bizplus.mes.domain.uom.dto.UomUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/uoms")
@RequiredArgsConstructor
public class UomController {

    private final UomService uomService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('UOM_READ')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.READ)
    public String viewList(Model model,
                           @RequestParam(required = false) String code,
                           @RequestParam(required = false) String name) {

        model.addAttribute("uoms", uomService.getUoms(code, name));

        return "pages/uom/list";
    }

    @GetMapping("/new")
    public String viewNew(Model model) {

        model.addAttribute("uomTypes", UomType.values());

        return "pages/uom/fragments/modal/create-content :: content";
    }

    @GetMapping("/{id}/edit")
    public String viewEdit(Model model, @PathVariable Long id) {

        model.addAttribute("uomTypes", UomType.values());
        model.addAttribute("uom", uomService.getUom(id));

        return "pages/uom/fragments/modal/edit-content :: content";
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_CREATE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createUom(@Valid UomCreateDto dto) {

        uomService.createUom(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_UPDATE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> updateUom(@PathVariable Long id,
                                                       @Valid UomUpdateDto dto) {

        uomService.updateUom(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('UOM_DELETE')")
    @UserAction(menu = MenuCode.UOM, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteUoms(@RequestBody List<Long> ids) {

        uomService.deleteUoms(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
