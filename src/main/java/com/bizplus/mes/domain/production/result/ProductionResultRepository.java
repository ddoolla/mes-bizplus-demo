package com.bizplus.mes.domain.production.result;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionResultRepository
        extends JpaRepository<ProductionResult, Long>, ProductionResultQueryRepository {
}
