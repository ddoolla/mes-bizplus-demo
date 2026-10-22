package com.bizplus.mes.domain.inspection.incoming;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingInspectionRepository
        extends JpaRepository<IncomingInspection, Long>, IncomingInspectionQueryRepository {
}
