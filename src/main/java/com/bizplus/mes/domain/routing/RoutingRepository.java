package com.bizplus.mes.domain.routing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutingRepository extends JpaRepository<Routing, Long>, RoutingQueryRepository {

    Optional<Routing> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
