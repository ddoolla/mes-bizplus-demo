package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.QRoleDto;
import com.bizplus.mes.domain.role.dto.RoleDto;
import com.bizplus.mes.domain.role.dto.RoleSearchDto;
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
import static com.bizplus.mes.domain.role.QRole.role;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleQueryRepositoryImpl implements RoleQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<RoleDto> findRoles(RoleSearchDto dto, Pageable pageable) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(role.deletedAt))
                .and(contains(role.code, dto.getCode()))
                .and(contains(role.name, dto.getName()));

        List<RoleDto> content = query
                .select(new QRoleDto(
                        role.id,
                        role.code,
                        role.name,
                        role.description
                ))
                .from(role)
                .where(searchCondition)
                .orderBy(role.createdAt.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(role.count())
                .from(role)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<RoleDto> findRole(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QRoleDto(
                                role.id,
                                role.code,
                                role.name,
                                role.description
                        ))
                        .from(role)
                        .where(
                                notDeleted(role.deletedAt),
                                eq(role.id, id)
                        )
                        .fetchOne()
        );
    }
}
