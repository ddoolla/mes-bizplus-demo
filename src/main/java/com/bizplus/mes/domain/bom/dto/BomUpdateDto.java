package com.bizplus.mes.domain.bom.dto;

import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BomUpdateDto {

    @NotNull
    private Long itemId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String remark;

    private List<BomItemUpdateDto> bomItems;
}
