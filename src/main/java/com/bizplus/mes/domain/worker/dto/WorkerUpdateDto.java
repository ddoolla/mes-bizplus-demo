package com.bizplus.mes.domain.worker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkerUpdateDto {

    @NotBlank
    private String code;
    private String remark;
}
