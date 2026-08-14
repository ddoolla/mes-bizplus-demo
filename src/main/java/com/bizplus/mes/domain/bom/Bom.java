package com.bizplus.mes.domain.bom;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "bom",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_id", "revision_no"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    @Column(nullable = false)
    private int revisionNo;

    private String remark;

    public Bom(Item item,
               String code,
               String name,
               int revisionNo,
               String remark) {
        this.item = item;
        this.code = code;
        this.name = name;
        this.revisionNo = revisionNo;
        this.remark = remark;
    }

    public void update(Item item,
                       String code,
                       String name,
                       String remark) {
        this.item = item;
        this.code = code;
        this.name = name;
        this.remark = remark;
    }
}
