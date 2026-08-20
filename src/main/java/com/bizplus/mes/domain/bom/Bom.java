package com.bizplus.mes.domain.bom;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bom extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String version;

    // 모두 false 가능 -> 사용자에게 기본 없음을 표시
    @Column(name = "is_primary")
    private boolean primary;

    private String remark;

    public Bom(Item item,
               String code,
               String name,
               String version,
               boolean primary,
               String remark) {
        this.item = item;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.remark = remark;
    }

    public void update(String code,
                       String name,
                       String version,
                       boolean primary,
                       String remark) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.remark = remark;
    }
}
