package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.user.dto.QUserDto;
import com.bizplus.mes.domain.user.dto.UserDto;
import com.bizplus.mes.domain.user.dto.UserSearchDto;
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
import static com.bizplus.mes.domain.user.QUser.user;
import static com.bizplus.mes.domain.user.role.QUserRole.userRole;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode departmentCode = new QCommonCode("departmentCode");
    private static final QCommonCode positionCode = new QCommonCode("positionCode");

    @Override
    public Page<UserDto> findUsers(UserSearchDto dto, Pageable pageable) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(user.deletedAt))
                .and(contains(user.userId, dto.getUserId()))
                .and(contains(user.name, dto.getName()));

        List<UserDto> content = query
                .select(new QUserDto(
                        user.id,
                        user.userId,
                        user.name,
                        user.email,
                        user.phone,
                        departmentCode.id,
                        departmentCode.name,
                        positionCode.id,
                        positionCode.name,
                        role.id,
                        role.name,
                        user.remark
                ))
                .from(user)
                .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                .innerJoin(userRole).on(user.id.eq(userRole.user.id))
                .innerJoin(role).on(userRole.role.id.eq(role.id))
                .where(searchCondition)
                .orderBy(user.userId.asc(), user.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(user.count())
                .from(user)
                .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                .innerJoin(userRole).on(user.id.eq(userRole.user.id))
                .innerJoin(role).on(userRole.role.id.eq(role.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<UserDto> findUser(Long id) {

        return Optional.ofNullable(
                query
                        .select(new QUserDto(
                                user.id,
                                user.userId,
                                user.name,
                                user.email,
                                user.phone,
                                departmentCode.id,
                                departmentCode.name,
                                positionCode.id,
                                positionCode.name,
                                role.id,
                                role.name,
                                user.remark
                        ))
                        .from(user)
                        .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                        .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                        .innerJoin(userRole).on(user.id.eq(userRole.user.id))
                        .innerJoin(role).on(userRole.role.id.eq(role.id))
                        .where(
                                notDeleted(user.deletedAt),
                                eq(user.id, id)
                        )
                        .fetchOne()
        );
    }
}
