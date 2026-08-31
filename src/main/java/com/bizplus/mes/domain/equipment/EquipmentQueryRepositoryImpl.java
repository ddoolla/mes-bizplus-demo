package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.equipment.dto.EquipmentDto;
import com.bizplus.mes.domain.equipment.dto.EquipmentSearchDto;
import com.bizplus.mes.domain.equipment.dto.QEquipmentDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.equipment.QEquipment.equipment;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EquipmentQueryRepositoryImpl implements EquipmentQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode equipmentTypeCode = new QCommonCode("equipmentTypeCode");

    @Override
    public Page<EquipmentDto> findEquipments(EquipmentSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(equipment.deletedAt))
                .and(contains(equipment.code, dto.getCode()))
                .and(contains(equipment.name, dto.getName()))
                .and(eq(equipment.type.id, dto.getTypeId()));

        List<EquipmentDto> content = query
                .select(new QEquipmentDto(
                        equipment.id,
                        equipment.code,
                        equipment.name,
                        equipment.specification,
                        equipment.manufacturer,
                        equipment.model,
                        equipment.serialNo,
                        equipment.location,
                        equipment.remark,
                        equipmentTypeCode.id,
                        equipmentTypeCode.code,
                        equipmentTypeCode.name
                ))
                .from(equipment)
                .leftJoin(equipmentTypeCode).on(equipment.type.id.eq(equipmentTypeCode.id))
                .where(searchCondition)
                .orderBy(equipment.code.asc(), equipment.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(equipment.count())
                .from(equipment)
                .leftJoin(equipmentTypeCode).on(equipment.type.id.eq(equipmentTypeCode.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<EquipmentDto> findEquipment(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QEquipmentDto(
                                equipment.id,
                                equipment.code,
                                equipment.name,
                                equipment.specification,
                                equipment.manufacturer,
                                equipment.model,
                                equipment.serialNo,
                                equipment.location,
                                equipment.remark,
                                equipmentTypeCode.id,
                                equipmentTypeCode.code,
                                equipmentTypeCode.name
                        ))
                        .from(equipment)
                        .leftJoin(equipmentTypeCode).on(equipment.type.id.eq(equipmentTypeCode.id))
                        .where(
                                notDeleted(equipment.deletedAt),
                                eq(equipment.id, id)
                        )
                        .fetchOne()
        );
    }
}
