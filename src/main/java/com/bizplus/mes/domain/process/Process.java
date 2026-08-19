package com.bizplus.mes.domain.process;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Process extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    public Process(String code,
                   String name,
                   String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void update(String code,
                       String name,
                       String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
