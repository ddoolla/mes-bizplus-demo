package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.QCommonCodeDto;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtil.*;
import static com.bizplus.mes.domain.code.common.QCommonCode.commonCode;
import static com.bizplus.mes.domain.code.group.QCodeGroup.codeGroup;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonCodeQueryRepositoryImpl implements CommonCodeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<CommonCodeDto> findCommonCodes(Long codeGroupId, String code, String name) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(commonCode.deletedAt))
                .and(eq(commonCode.group.id, codeGroupId))
                .and(contains(commonCode.code, code))
                .and(contains(commonCode.name, name));

        return query
                .select(new QCommonCodeDto(
                        commonCode.id,
                        commonCode.code,
                        commonCode.name,
                        commonCode.description
                ))
                .from(commonCode)
                .where(searchCondition)
                .fetch();
    }

    @Override
    public List<CommonCodeDto> findCommonCodes(CodeGroupKey groupKey) {

        return query
                .select(new QCommonCodeDto(
                        commonCode.id,
                        commonCode.code,
                        commonCode.name,
                        commonCode.description
                ))
                .from(commonCode)
                .innerJoin(codeGroup).on(commonCode.group.id.eq(codeGroup.id))
                .where(
                        notDeleted(commonCode.deletedAt),
                        eq(codeGroup.groupKey, groupKey)
                )
                .orderBy(commonCode.name.asc())
                .fetch();
    }
}
