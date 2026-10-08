package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lot extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(unique = true, nullable = false)
    private String no;

    public Lot(Item item, String no) {
        this.item = item;
        this.no = no;
    }
}
