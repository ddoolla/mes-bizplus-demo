package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.code.group.CodeGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "common_codes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"group_id", "code"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private CodeGroup group;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer sortOrder;

    public CommonCode(CodeGroup group,
                      String code,
                      String name,
                      String description,
                      Integer sortOrder) {
        this.group = group;
        this.code = code;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
