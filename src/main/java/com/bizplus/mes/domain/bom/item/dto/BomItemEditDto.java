package com.bizplus.mes.domain.bom.item.dto;

import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BomItemEditDto {

    private BomItemDto bomItem;
    private List<ConvertibleUomDto> convertibleUoms;
}
