package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 생산지시 당시 공정 정보 스냅샷
* */
@Entity
@Table(name = "production_order_processes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionOrderProcess extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id")
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routing_process_id")
    private RoutingProcess routingProcess; // 공정 단계 참조용

    // 생산지시 시점의 공정 정보 스냅샷
    private Long processId;
    private String processCode;
    private String processName;
    private int processStepNo;

    public ProductionOrderProcess(ProductionOrder productionOrder,
                                  RoutingProcess routingProcess,
                                  Long processId,
                                  String processCode,
                                  String processName,
                                  int processStepNo) {
        this.productionOrder = productionOrder;
        this.routingProcess = routingProcess;
        this.processId = processId;
        this.processCode = processCode;
        this.processName = processName;
        this.processStepNo = processStepNo;
    }
}
