package com.bizplus.mes.domain.process.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessDto {

    private Long id;
    private String code;
    private String name;
    private String description;
}
