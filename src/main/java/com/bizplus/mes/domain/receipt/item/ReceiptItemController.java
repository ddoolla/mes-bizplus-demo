package com.bizplus.mes.domain.receipt.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;
}
