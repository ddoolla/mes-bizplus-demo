package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "inventories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private BigDecimal quantity;

    private BigDecimal reservedQuantity; // 예약 재고 (확장)

    public Inventory(Item item,
                     BigDecimal quantity,
                     BigDecimal reservedQuantity) {
        this.item = item;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public void updateQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
