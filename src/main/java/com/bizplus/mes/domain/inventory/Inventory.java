package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
* * 로트 관리 품목
*   - 품목별 한 개의 로트만 재고로 관리
*   - item_id + lot_id 복합 고유기
*
* * 로트 관리 안하는 품목
*   - 품목별 한 개의 재고만 관리
*   - item_id + null (lot_id) = 프로그램 로직에서 제어
* */
@Entity
@Table(
        name = "inventories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_id", "lot_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    private BigDecimal quantity;

    private BigDecimal reservedQuantity; // 예약 재고 (확장)

    public Inventory(Item item,
                     Lot lot,
                     BigDecimal quantity,
                     BigDecimal reservedQuantity) {
        this.item = item;
        this.lot = lot;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public void updateQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
