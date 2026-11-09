package com.bizplus.mes.domain.lot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LotCreateDto {

    private Long itemId;
    private String no;
    private BigDecimal initialQuantity;
    private LocalDate manufactureDate;
    private LocalDate expirationDate;
}
