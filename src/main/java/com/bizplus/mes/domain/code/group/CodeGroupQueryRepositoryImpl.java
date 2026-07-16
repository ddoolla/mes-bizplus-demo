package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;
import com.bizplus.mes.domain.code.group.dto.QCodeGroupDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtil.contains;
import static com.bizplus.mes.common.util.PredicateUtil.eq;
import static com.bizplus.mes.domain.code.group.QCodeGroup.codeGroup;
import static com.bizplus.mes.domain.menu.QMenu.menu;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeGroupQueryRepositoryImpl implements CodeGroupQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<CodeGroupDto> findCodeGroups(String menuName, String name) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(contains(menu.name, menuName))
                .and(contains(codeGroup.name, name));

        return query
                .select(new QCodeGroupDto(
                        codeGroup.id,
                        codeGroup.name,
                        menu.name
                ))
                .from(codeGroup)
                .innerJoin(menu).on(codeGroup.menu.id.eq(menu.id))
                .where(searchCondition)
                .fetch();
    }

    @Override
    public Optional<CodeGroupDto> findCodeGroup(Long id) {

        return Optional.ofNullable(
                query
                        .select(new QCodeGroupDto(
                                codeGroup.id,
                                codeGroup.name,
                                menu.name
                        ))
                        .from(codeGroup)
                        .where(eq(codeGroup.id, id))
                        .fetchOne()
        );
    }
}
