package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lot extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(unique = true, nullable = false)
    private String no;

    @Column(precision = 38, scale = 10)
    private BigDecimal initialQuantity; // 초기 수량 저장 (재고로 사용 X)

    private LocalDate manufactureDate; // 제조일
    private LocalDate expirationDate; // 유효기간

    private String remark;

    public Lot(Item item,
               String no,
               BigDecimal initialQuantity,
               LocalDate manufactureDate,
               LocalDate expirationDate,
               String remark) {
        this.item = item;
        this.initialQuantity = initialQuantity;
        this.no = no;
        this.manufactureDate = manufactureDate;
        this.expirationDate = expirationDate;
        this.remark = remark;
    }

    public void update(LocalDate expirationDate, String remark) {
        this.expirationDate = expirationDate;
        this.remark = remark;
    }

    public void updateInitialQuantity(BigDecimal initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public void updateDates(LocalDate manufactureDate, LocalDate expirationDate) {
        this.manufactureDate = manufactureDate;
        this.expirationDate = expirationDate;
    }
}
