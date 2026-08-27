package com.bizplus.mes.domain.uom.conversion.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UomConversionListDto {

    private List<UomConversionDto> uomConversions;
    private Pagination pagination;
}
