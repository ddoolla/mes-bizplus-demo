package com.bizplus.mes.domain.bom.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BomListDto {

    private List<BomDto> boms;
    private Pagination pagination;
}
