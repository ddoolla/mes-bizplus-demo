package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.domain.routing.process.dto.QRoutingProcessDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.process.QProcess.process;
import static com.bizplus.mes.domain.routing.process.QRoutingProcess.routingProcess;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoutingProcessQueryRepositoryImpl implements RoutingProcessQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<RoutingProcessDto> findRoutingProcesses(Long routingId) {
        return query
                .select(new QRoutingProcessDto(
                        routingProcess.id,
                        routingProcess.stepNo,
                        process.id,
                        process.code,
                        process.name
                ))
                .from(routingProcess)
                .innerJoin(process).on(routingProcess.process.id.eq(process.id))
                .where(
                        eq(routingProcess.routing.id, routingId)
                )
                .orderBy(routingProcess.stepNo.asc())
                .fetch();
    }
}
