package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemListDto {

    private List<ItemDto> items;
    private Pagination pagination;
}
