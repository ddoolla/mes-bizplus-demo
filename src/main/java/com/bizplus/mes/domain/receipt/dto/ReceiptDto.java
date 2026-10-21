package com.bizplus.mes.domain.receipt.dto;

import com.bizplus.mes.domain.receipt.ReceiptStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReceiptDto {

    private final Long id;
    private final String receiptNo;
    private final LocalDate date;
    private final ReceiptStatus status;
    private final String remark;
    private final PartnerInfo partner;

    @QueryProjection
    public ReceiptDto(Long id,
                      String receiptNo,
                      LocalDate date,
                      ReceiptStatus status,
                      String remark,
                      Long partnerId,
                      String partnerName) {
        this.id = id;
        this.receiptNo = receiptNo;
        this.date = date;
        this.status = status;
        this.remark = remark;
        this.partner = new PartnerInfo(partnerId, partnerName);
    }

    public record PartnerInfo(Long id, String name) {
    }
}
