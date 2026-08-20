package com.bizplus.mes.domain.bom.dto;

import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class BomUpdateDto {

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String remark;
    private final List<BomItemUpdateDto> bomItems;

    public BomUpdateDto(String code,
                        String name,
                        String version,
                        Boolean primary,
                        String remark,
                        List<BomItemUpdateDto> bomItems) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.remark = remark;
        this.bomItems = bomItems == null ? List.of() : bomItems;
    }
}
