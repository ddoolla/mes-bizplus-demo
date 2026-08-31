package com.bizplus.mes.domain.worker;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.worker.dto.QWorkerDto;
import com.bizplus.mes.domain.worker.dto.WorkerDto;
import com.bizplus.mes.domain.worker.dto.WorkerSearchDto;
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
import static com.bizplus.mes.domain.user.QUser.user;
import static com.bizplus.mes.domain.worker.QWorker.worker;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkerQueryRepositoryImpl implements WorkerQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode departmentCode = new QCommonCode("departmentCode");
    private static final QCommonCode positionCode = new QCommonCode("positionCode");

    @Override
    public Page<WorkerDto> findWorkers(WorkerSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(worker.deletedAt))
                .and(contains(worker.code, dto.getCode()))
                .and(contains(user.name, dto.getUserName()))
                .and(eq(departmentCode.id, dto.getDepartmentId()))
                .and(eq(positionCode.id, dto.getPositionId()));

        List<WorkerDto> content = query
                .select(new QWorkerDto(
                        worker.id,
                        worker.code,
                        worker.remark,
                        user.id,
                        user.name,
                        departmentCode.name,
                        positionCode.name,
                        user.phone,
                        user.email
                ))
                .from(worker)
                .innerJoin(user).on(worker.user.id.eq(user.id))
                .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                .where(searchCondition)
                .orderBy(worker.code.asc(), user.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(worker.count())
                .from(worker)
                .innerJoin(user).on(worker.user.id.eq(user.id))
                .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<WorkerDto> findWorker(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QWorkerDto(
                                worker.id,
                                worker.code,
                                worker.remark,
                                user.id,
                                user.name,
                                departmentCode.name,
                                positionCode.name,
                                user.phone,
                                user.email
                        ))
                        .from(worker)
                        .innerJoin(user).on(worker.user.id.eq(user.id))
                        .leftJoin(departmentCode).on(user.department.id.eq(departmentCode.id))
                        .leftJoin(positionCode).on(user.position.id.eq(positionCode.id))
                        .where(
                                notDeleted(worker.deletedAt),
                                eq(worker.id, id)
                        )
                        .fetchOne()
        );
    }
}
