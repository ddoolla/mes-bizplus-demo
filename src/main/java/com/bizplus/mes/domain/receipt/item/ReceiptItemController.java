package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;

    @GetMapping("/receipt-items/modal/select")
    public String viewSelectModal(Model model, ReceiptItemSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", receiptItemService.getAllForInspection(dto, pageable));

        return "pages/receipt-item/modal/select :: list";
    }
}
