package com.bizplus.mes.domain.uom;

import com.bizplus.mes.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 단위 엔터티 (unit of measure)
 */
@Entity
@Table(name = "uom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Uom extends BaseEntity {

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

    private Integer decimalPlaces;

    public Uom(String code,
               String name,
               UomType type,
               Integer decimalPlaces) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.decimalPlaces = decimalPlaces == null ? 0 : decimalPlaces;
    }

    public void update(String code,
                       String name,
                       UomType type,
                       Integer decimalPlaces) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.decimalPlaces = decimalPlaces == null ? 0 : decimalPlaces;
    }
}
