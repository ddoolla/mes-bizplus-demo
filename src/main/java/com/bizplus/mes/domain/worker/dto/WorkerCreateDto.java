package com.bizplus.mes.domain.worker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkerCreateDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String code;
    private String remark;
}
