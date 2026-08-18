package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code_id")
    private CommonCode category;

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

    private boolean lotManaged;

    public Item(CommonCode category,
                Uom uom,
                String code,
                String name,
                ItemType type,
                String specification,
                String remark,
                boolean lotManaged) {
        this.category = category;
        this.uom = uom;
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.remark = remark;
        this.lotManaged = lotManaged;
    }

    public void update(CommonCode category,
                       Uom uom,
                       String code,
                       String name,
                       ItemType type,
                       String specification,
                       String remark) {
        this.category = category;
        this.uom = uom;
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.remark = remark;
    }

    public void updateLotManaged(boolean lotManaged) {
        this.lotManaged = lotManaged;
    }
}
