package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.domain.inspection.InspectionType;
import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.Objects;

@Getter
public class InspectionSpecDto {

    private final Long id;
    private final String code;
    private final String name;
    private final InspectionType type;
    private final String version;
    private final Boolean isDefault;
    private final String remark;
    private final ItemInfo item;
    private final ProcessInfo process;

    @QueryProjection
    public InspectionSpecDto(Long id,
                             String code,
                             String name,
                             InspectionType type,
                             String version,
                             String remark,
                             Long itemId,
                             Long defaultIqcSpecId,
                             Long defaultOqcSpecId,
                             String itemCode,
                             String itemName,
                             String itemCategory,
                             ItemType itemType,
                             Long processId,
                             String processCode,
                             String processName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.version = version;
        this.isDefault = switch (type) {
            case INCOMING -> Objects.equals(id, defaultIqcSpecId);
            case OUTGOING -> Objects.equals(id, defaultOqcSpecId);
            case IN_PROCESS -> false;
        };
        this.remark = remark;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemCategory,
                itemType
        );
        this.process = new ProcessInfo(
                processId,
                processCode,
                processName
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           String category,
                           ItemType type) {
    }

    public record ProcessInfo(Long id,
                              String code,
                              String name) {
    }

}
