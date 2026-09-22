package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.item.file.dto.ItemFileCreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemCreateDto {

    @NotNull
    private Long uomId;
    private Long categoryId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private ItemType type;
    private String specification;

    @PositiveOrZero
    private BigDecimal unitPrice;
    private String remark;
    private boolean lotManaged;

    private List<ItemFileCreateDto> itemFiles;
}
