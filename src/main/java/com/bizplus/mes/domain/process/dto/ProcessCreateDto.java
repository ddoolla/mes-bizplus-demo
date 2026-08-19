package com.bizplus.mes.domain.process.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessCreateDto {

    private String code;
    private String name;
    private String description;
}
