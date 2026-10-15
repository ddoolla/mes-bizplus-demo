package com.bizplus.mes.domain.inventory.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    RECEIPT("입고", StockDirection.IN),
    SHIPMENT("출고", StockDirection.OUT),
    PRODUCTION_IN("생산입고", StockDirection.IN),
    PRODUCTION_OUT("생산투입", StockDirection.OUT),
    ADJUSTMENT_INCREASE("재고조정(증가)", StockDirection.IN),
    ADJUSTMENT_DECREASE("재고조정(감소)", StockDirection.OUT),
    CUSTOMER_RETURN("고객반품", StockDirection.IN),
    SUPPLIER_RETURN("공급처반품", StockDirection.OUT);

    private final String description;
    private final StockDirection direction;

    public BigDecimal getSignedQuantity(BigDecimal quantity) {
        return direction.apply(quantity);
    }
}
