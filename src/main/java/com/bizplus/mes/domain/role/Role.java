package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    public Role(String code,
                String name,
                String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void update(String name,
                       String description) {
        this.name = name;
        this.description = description;
    }
}
