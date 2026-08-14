package com.bizplus.mes.domain.bom;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.bom.dto.BomCreateDto;
import com.bizplus.mes.domain.bom.dto.BomSearchDto;
import com.bizplus.mes.domain.bom.dto.BomUpdateDto;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
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
@RequestMapping("/boms")
@RequiredArgsConstructor
public class BomController {

    private final BomService bomService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('BOM_READ')")
    @UserAction(menu = MenuCode.BOM, type = ActionType.READ)
    public String viewList(Model model,
                           BomSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("data", bomService.getBoms(dto, pageable));

        return "pages/bom/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BOM_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("bom", bomService.getBom(id));

        return "pages/bom/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('BOM_READ')")
    public String viewNew(Model model) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemGroup.PRODUCT.getTypes());

        return "pages/bom/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('BOM_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("bom", bomService.getBom(id));

        return "pages/bom/edit";
    }

    @GetMapping("check-code")
    @ResponseBody
    public boolean checkBomCode(@RequestParam(required = false) Long id,
                                @RequestParam String code) {
        return bomService.checkCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOM_CREATE')")
    @UserAction(menu = MenuCode.BOM, type = ActionType.CREATE)
    public String createBom(@Valid BomCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = bomService.createBom(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/boms/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('BOM_UPDATE')")
    @UserAction(menu = MenuCode.BOM, type = ActionType.UPDATE)
    public String updateBom(@PathVariable Long id,
                            @Valid BomUpdateDto dto,
                            RedirectAttributes reAtt) {
        bomService.updateBom(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/boms/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('BOM_DELETE')")
    @UserAction(menu = MenuCode.BOM, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteBom(@RequestBody List<Long> ids) {
        bomService.deleteBoms(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
