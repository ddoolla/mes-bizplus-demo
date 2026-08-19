package com.bizplus.mes.domain.process;

import com.bizplus.mes.domain.process.dto.ProcessSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.contains;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.process.QProcess.process;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessQueryRepositoryImpl implements ProcessQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Process> findProcesses(ProcessSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(process.deletedAt))
                .and(contains(process.code, dto.getCode()))
                .and(contains(process.name, dto.getName()));

        List<Process> content = query
                .selectFrom(process)
                .where(searchCondition)
                .orderBy(process.code.asc(), process.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(process.count())
                .from(process)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
