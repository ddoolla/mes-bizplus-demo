package com.bizplus.mes.domain.log.auth;

import com.bizplus.mes.domain.log.auth.dto.QUserAuthLogDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.log.auth.QUserAuthLog.userAuthLog;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthLogQueryRepositoryImpl implements UserAuthLogQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<UserAuthLogDto> findUserAuthLogs(UserAuthLogSearchDto dto, Pageable pageable) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(contains(userAuthLog.userId, dto.getUserId()))
                .and(startDateGoe(userAuthLog.loginAt, dto.getStartDate()))
                .and(endDateLoe(userAuthLog.loginAt, dto.getEndDate()));

        List<UserAuthLogDto> content = query
                .select(new QUserAuthLogDto(
                        userAuthLog.id,
                        userAuthLog.userId,
                        userAuthLog.userName,
                        userAuthLog.ipAddress,
                        userAuthLog.loginAt,
                        userAuthLog.logoutAt,
                        userAuthLog.logoutType
                ))
                .from(userAuthLog)
                .where(searchCondition)
                .orderBy(userAuthLog.loginAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(userAuthLog.count())
                .from(userAuthLog)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
