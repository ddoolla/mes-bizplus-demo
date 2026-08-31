package com.bizplus.mes.domain.equipment.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EquipmentListDto {

    private List<EquipmentDto> equipments;
    private Pagination pagination;
}
