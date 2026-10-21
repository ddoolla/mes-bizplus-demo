package com.bizplus.mes.domain.material.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/material-receipts")
@RequiredArgsConstructor
public class MaterialReceiptController {

    private final MaterialReceiptService materialReceiptService;
}
