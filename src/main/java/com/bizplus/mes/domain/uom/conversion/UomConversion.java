package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
* 물리 삭제 데이터
* */
@Entity
@Table(
        name = "uom_conversions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"from_uom_id", "to_uom_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UomConversion extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_uom_id", nullable = false)
    private Uom fromUom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_uom_id", nullable = false)
    private Uom toUom;

    @Column(precision = 38, scale = 10)
    private BigDecimal factor;

    public UomConversion(Uom fromUom, Uom toUom, BigDecimal factor) {
        this.fromUom = fromUom;
        this.toUom = toUom;
        this.factor = factor;
    }

    public void update(BigDecimal factor) {
        this.factor = factor;
    }
}
