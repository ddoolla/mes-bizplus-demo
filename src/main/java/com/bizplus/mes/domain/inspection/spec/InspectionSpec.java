package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.process.Process;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inspection_specs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InspectionSpec extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private InspectionType type;

    private String version;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    private String remark;

    public InspectionSpec(Item item,
                          Process process,
                          String code,
                          String name,
                          InspectionType type,
                          String version,
                          boolean primary,
                          String remark) {
        this.item = item;
        this.process = process;
        this.code = code;
        this.name = name;
        this.type = type;
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
