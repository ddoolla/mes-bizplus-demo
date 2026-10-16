package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.work.order.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_inputs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaterialInput extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialInputType type;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialInputStatus status;

    @Column(nullable = false, precision = 38, scale = 10)
    private BigDecimal quantity;

    private LocalDateTime inputAt;

    public MaterialInput(WorkOrder workOrder,
                         Item item,
                         Lot lot,
                         MaterialInputType type,
                         MaterialInputStatus status,
                         BigDecimal quantity,
                         LocalDateTime inputAt) {
        this.workOrder = workOrder;
        this.item = item;
        this.lot = lot;
        this.type = type;
        this.status = status;
        this.quantity = quantity;
        this.inputAt = inputAt;
    }
}
