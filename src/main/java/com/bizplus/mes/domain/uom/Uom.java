package com.bizplus.mes.domain.uom;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 단위 엔터티 (unit of measure)
* */
@Entity
@Table(name = "uom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Uom extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UomType type;

    private Integer scale;

    public Uom(String code,
               String name,
               UomType type,
               Integer scale) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.scale = scale == null ? 0 : scale;
    }

    public void update(String code,
                       String name,
                       UomType type,
                       Integer scale) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.scale = scale == null ? 0 : scale;
    }
}
