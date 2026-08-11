package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemInventorySearchDto {

    private Long categoryId;
    private String code;
    private String name;
    private ItemType type;
}
