package com.bizplus.mes.domain.receipt.item.dto;

import com.bizplus.mes.common.util.AuditUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ReceiptItemExcelDto {

    private final LocalDate receiptDate;
    private final String partnerName;
    private final String itemCode;
    private final String itemName;
    private final String lotNo;
    private final String uomCode;
    private final Integer uomScale;
    private final BigDecimal quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal amount;
    private final String createdBy;
    private final String remark;

    @QueryProjection
    public ReceiptItemExcelDto(LocalDate receiptDate,
                               String partnerName,
                               String itemCode,
                               String itemName,
                               String lotNo,
                               String uomCode,
                               Integer uomScale,
                               BigDecimal quantity,
                               BigDecimal unitPrice,
                               BigDecimal amount,
                               String createdBy,
                               String remark) {
        this.receiptDate = receiptDate;
        this.partnerName = partnerName;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.lotNo = lotNo;
        this.uomCode = uomCode;
        this.uomScale = uomScale;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.createdBy = AuditUtils.extractName(createdBy);
        this.remark = remark;
    }
}
