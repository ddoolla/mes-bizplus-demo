package com.bizplus.mes.domain.sales.order.dto;

import com.bizplus.mes.domain.sales.order.SalesOrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SalesOrderDto {

    private final Long id;
    private final String orderNo;
    private final LocalDate date;
    private final LocalDate dueDate;
    private final String remark;
    private final SalesOrderStatus status;
    private final PartnerInfo partner;

    @QueryProjection
    public SalesOrderDto(Long id,
                         String orderNo,
                         LocalDate date,
                         LocalDate dueDate,
                         String remark,
                         SalesOrderStatus status,
                         Long partnerId,
                         String partnerName) {
        this.id = id;
        this.orderNo = orderNo;
        this.date = date;
        this.dueDate = dueDate;
        this.remark = remark;
        this.status = status;
        this.partner = new PartnerInfo(partnerId, partnerName);
    }

    public record PartnerInfo(
            Long id,
            String name
    ) {
    }
}
