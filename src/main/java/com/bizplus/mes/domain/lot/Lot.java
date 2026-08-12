package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String no;

    private LocalDateTime manufactureDate;

    private LocalDateTime expireDate;

    @Enumerated(EnumType.STRING)
    private LotStatus status;

    public Lot(String no,
               LocalDateTime manufactureDate,
               LocalDateTime expireDate,
               LotStatus status) {
        this.no = no;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.status = status;
    }
}
