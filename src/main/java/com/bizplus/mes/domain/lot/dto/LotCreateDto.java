package com.bizplus.mes.domain.lot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LotCreateDto {

    private Long itemId;
    private String no;
    private LocalDate manufactureDate;
    private LocalDate expirationDate;
}
