package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.domain.log.action.dto.QUserActionLogDto;
import com.bizplus.mes.domain.log.action.dto.UserActionLogDto;
import com.bizplus.mes.domain.log.action.dto.UserActionLogSearchDto;
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
import static com.bizplus.mes.domain.log.action.QUserActionLog.userActionLog;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActionLogQueryRepositoryImpl implements UserActionLogQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<UserActionLogDto> findUserActionLogs(UserActionLogSearchDto dto, Pageable pageable) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(contains(userActionLog.userId, dto.getUserId()))
                .and(eq(userActionLog.menu, dto.getMenuCode()))
                .and(eq(userActionLog.type, dto.getType()))
                .and(startDateGoe(userActionLog.createdAt, dto.getStartDate()))
                .and(endDateLoe(userActionLog.createdAt, dto.getEndDate()));

        List<UserActionLogDto> content = query
                .select(new QUserActionLogDto(
                        userActionLog.id,
                        userActionLog.userId,
                        userActionLog.userName,
                        userActionLog.menu,
                        userActionLog.type,
                        userActionLog.result,
                        userActionLog.ipAddress,
                        userActionLog.createdAt
                ))
                .from(userActionLog)
                .where(searchCondition)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(userActionLog.count())
                .from(userActionLog)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
