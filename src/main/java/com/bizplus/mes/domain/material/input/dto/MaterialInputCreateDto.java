package com.bizplus.mes.domain.material.input.dto;

import com.bizplus.mes.domain.material.input.MaterialInputStatus;
import com.bizplus.mes.domain.material.input.MaterialInputType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MaterialInputCreateDto {

    @NotNull
    private Long itemId;
    private Long lotId;

    @NotNull
    private MaterialInputType type;

    @NotNull
    private MaterialInputStatus status;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private LocalDateTime inputAt;
}
