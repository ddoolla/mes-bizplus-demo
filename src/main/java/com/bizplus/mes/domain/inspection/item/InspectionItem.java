package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inspection_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InspectionItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code_id")
    private CommonCode group;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    public InspectionItem(CommonCode group,
                          String code,
                          String name,
                          String description) {
        this.group = group;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void update(CommonCode group,
                       String code,
                       String name,
                       String description) {
        this.group = group;
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
