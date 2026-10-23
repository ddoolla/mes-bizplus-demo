package com.bizplus.mes.domain.receipt.dto;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemUpdateDto;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReceiptUpdateDto {

    private final Long partnerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final String remark;
    private final List<ReceiptItemUpdateDto> receiptItems;

    public ReceiptUpdateDto(Long partnerId,
                            LocalDate date,
                            String remark,
                            List<ReceiptItemUpdateDto> receiptItems) {
        this.partnerId = partnerId;
        this.date = date;
        this.remark = remark;
        this.receiptItems = receiptItems == null ? List.of() : receiptItems;
    }
}
