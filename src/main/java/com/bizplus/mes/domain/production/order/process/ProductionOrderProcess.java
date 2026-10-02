package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.production.order.ProductionOrder;
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
    @JoinColumn(name = "process_id")
    private Process process; // 공정 참조값

    // 생산지시 시점의 공정 정보
    private String code;
    private String name;
    private int stepNo;

    public ProductionOrderProcess(ProductionOrder productionOrder,
                                  Process process,
                                  String code,
                                  String name,
                                  int stepNo) {
        this.productionOrder = productionOrder;
        this.process = process;
        this.code = code;
        this.name = name;
        this.stepNo = stepNo;
    }
}
