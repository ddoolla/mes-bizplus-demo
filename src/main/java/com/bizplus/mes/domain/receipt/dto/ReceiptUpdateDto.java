package com.bizplus.mes.domain.receipt.dto;

import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemUpdateDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptUpdateDto {

    @NotNull
    private Long partnerId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String remark;

    @NotNull
    @NotEmpty
    private List<ReceiptItemUpdateDto> receiptItems;
}
