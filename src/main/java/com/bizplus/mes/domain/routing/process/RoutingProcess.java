package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.routing.Routing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 물리삭제 데이터
 * */
@Entity
@Table(name = "routing_processes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutingProcess extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routing_id", nullable = false)
    private Routing routing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;

    private int stepNo;

    public RoutingProcess(Routing routing,
                          Process process,
                          int stepNo) {
        this.routing = routing;
        this.process = process;
        this.stepNo = stepNo;
    }

    public void updateStep(int stepNo) {
        this.stepNo = stepNo;
    }
}
