package com.bizplus.mes.domain.bom.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BomItemCreateDto {

    @NotEmpty
    private List<Long> itemIds;
}
