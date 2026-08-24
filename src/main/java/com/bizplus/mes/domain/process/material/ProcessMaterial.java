package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
 * 물리삭제 데이터
 * */
@Entity
@Table(name = "process_materials")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessMaterial extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routing_process_id", nullable = false)
    private RoutingProcess routingProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private ConsumptionMethod consumptionMethod;

    public ProcessMaterial(RoutingProcess routingProcess,
                           Item item,
                           Uom uom,
                           BigDecimal quantity,
                           ConsumptionMethod consumptionMethod) {
        this.routingProcess = routingProcess;
        this.item = item;
        this.uom = uom;
        this.quantity = quantity;
        this.consumptionMethod = consumptionMethod;
    }

    public void update(Uom uom,
                       BigDecimal quantity,
                       ConsumptionMethod consumptionMethod) {
        this.uom = uom;
        this.quantity = quantity;
        this.consumptionMethod = consumptionMethod;
    }
}
