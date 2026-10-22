package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.receipt.dto.ReceiptCreateDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptUpdateDto;
import com.bizplus.mes.domain.receipt.facade.ReceiptUpdateService;
import com.bizplus.mes.domain.receipt.item.ReceiptItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final MessageService messageService;
    private final CommonCodeService commonCodeService;
    private final ReceiptService receiptService;
    private final ReceiptItemService receiptItemService;

    private final ReceiptUpdateService receiptUpdateService;

    @GetMapping
    @PreAuthorize("hasAuthority('RECEIPT_READ')")
    @UserAction(menu = MenuCode.RECEIPT, type = ActionType.READ)
    public String viewList(Model model, ReceiptSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", receiptService.getAll(dto, pageable));

        return "pages/receipt/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('RECEIPT_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("receipt", receiptService.getById(id));
        model.addAttribute("receiptItems", receiptItemService.getAll(id));

        return "pages/receipt/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('RECEIPT_CREATE')")
    public String viewNew() {
        return "pages/receipt/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('RECEIPT_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("receipt", receiptService.getById(id));
        model.addAttribute("receiptItemData", receiptItemService.getAllForEdit(id));
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("materialTypes", ItemGroup.MATERIAL.getTypes());

        return "pages/receipt/edit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('RECEIPT_CREATE')")
    @UserAction(menu = MenuCode.RECEIPT, type = ActionType.CREATE)
    public String create(@Valid ReceiptCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = receiptService.create(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/receipts/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('RECEIPT_UPDATE')")
    @UserAction(menu = MenuCode.RECEIPT, type = ActionType.UPDATE)
    public String update(@PathVariable Long id, @Valid ReceiptUpdateDto dto, RedirectAttributes reAtt) {
        receiptUpdateService.update(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/receipts/{id}";
    }
}
