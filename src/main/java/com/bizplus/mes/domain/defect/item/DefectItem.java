package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "defect_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefectItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_type_code_id")
    private CommonCode type;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    private String remark;

    public DefectItem(CommonCode type,
                      String code,
                      String name,
                      String description,
                      String remark) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.description = description;
        this.remark = remark;
    }

    public void update(CommonCode type,
                       String code,
                       String name,
                       String description,
                       String remark) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.description = description;
        this.remark = remark;
    }
}
