package com.bizplus.mes.domain.routing.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutingProcessRepository extends
        JpaRepository<RoutingProcess, Long>, RoutingProcessQueryRepository {

    List<RoutingProcess> findByRoutingIdAndDeletedAtIsNull(Long routingId);
}
