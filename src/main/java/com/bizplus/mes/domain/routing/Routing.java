package com.bizplus.mes.domain.routing;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 제품 공정 경로 테이블
* */
@Entity
@Table(name = "routings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routing extends SoftDeletableEntity {

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

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    private String description;

    public Routing(Item item,
                   String code,
                   String name,
                   String version,
                   boolean primary,
                   String description) {
        this.item = item;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.description = description;
    }

    public void update(String code,
                       String name,
                       String version,
                       boolean primary,
                       String description) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = primary;
        this.description = description;
    }
}
