package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    private String specification;

    private String remark;

    public Item(Uom uom,
                String code,
                String name,
                ItemType type,
                String specification,
                String remark) {
        this.uom = uom;
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.remark = remark;
    }

    public void update(Uom uom,
                       String code,
                       String name,
                       ItemType type,
                       String specification,
                       String remark) {
        this.uom = uom;
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.remark = remark;
    }
}
