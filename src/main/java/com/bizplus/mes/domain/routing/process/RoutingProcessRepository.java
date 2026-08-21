package com.bizplus.mes.domain.routing.process;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutingProcessRepository extends
        JpaRepository<RoutingProcess, Long>, RoutingProcessQueryRepository {
}
