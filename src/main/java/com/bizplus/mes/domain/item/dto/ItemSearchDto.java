package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemSearchDto {

    private ItemGroup group;
    private Long categoryId;
    private String code;
    private String name;
    private ItemType type;
}
