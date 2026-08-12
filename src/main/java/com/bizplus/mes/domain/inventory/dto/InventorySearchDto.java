package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventorySearchDto {

    private Long categoryCodeId;
    private String itemCode;
    private String itemName;
    private ItemType itemType;
}
