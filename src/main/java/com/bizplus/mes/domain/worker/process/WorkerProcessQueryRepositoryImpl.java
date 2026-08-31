package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.domain.worker.process.dto.QWorkerProcessDto;
import com.bizplus.mes.domain.worker.process.dto.WorkerProcessDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.process.QProcess.process;
import static com.bizplus.mes.domain.worker.process.QWorkerProcess.workerProcess;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkerProcessQueryRepositoryImpl implements WorkerProcessQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<WorkerProcessDto> findWorkerProcesses(Long workerId) {
        return query
                .select(new QWorkerProcessDto(
                        workerProcess.id,
                        process.id,
                        process.code,
                        process.name,
                        process.description
                ))
                .from(workerProcess)
                .innerJoin(process).on(workerProcess.process.id.eq(process.id))
                .where(
                        eq(workerProcess.worker.id, workerId)
                )
                .fetch();
    }
}
