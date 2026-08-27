package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.domain.uom.QUom;
import com.bizplus.mes.domain.uom.conversion.dto.QUomConversionDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionSearchDto;
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

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.uom.conversion.QUomConversion.uomConversion;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UomConversionQueryRepositoryImpl implements UomConversionQueryRepository {

    private final JPAQueryFactory query;

    private static final QUom fromUom = new QUom("fromUom");
    private static final QUom toUom = new QUom("toUom");

    @Override
    public Page<UomConversionDto> findUomConversions(UomConversionSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(eq(fromUom.id, dto.getFromUomId()))
                .and(eq(toUom.id, dto.getToUomId()));

        List<UomConversionDto> content = query
                .select(new QUomConversionDto(
                        uomConversion.id,
                        uomConversion.factor,
                        fromUom.id,
                        fromUom.code,
                        fromUom.name,
                        toUom.id,
                        toUom.code,
                        toUom.name
                ))
                .from(uomConversion)
                .innerJoin(fromUom).on(uomConversion.fromUom.id.eq(fromUom.id))
                .innerJoin(toUom).on(uomConversion.toUom.id.eq(toUom.id))
                .where(searchCondition)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(uomConversion.count())
                .from(uomConversion)
                .innerJoin(fromUom).on(uomConversion.fromUom.id.eq(fromUom.id))
                .innerJoin(toUom).on(uomConversion.toUom.id.eq(toUom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<UomConversionDto> findUomConversion(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QUomConversionDto(
                                uomConversion.id,
                                uomConversion.factor,
                                fromUom.id,
                                fromUom.code,
                                fromUom.name,
                                toUom.id,
                                toUom.code,
                                toUom.name
                        ))
                        .from(uomConversion)
                        .innerJoin(fromUom).on(uomConversion.fromUom.id.eq(fromUom.id))
                        .innerJoin(toUom).on(uomConversion.toUom.id.eq(toUom.id))
                        .where(eq(uomConversion.id, id))
                        .fetchOne()
        );
    }
}
