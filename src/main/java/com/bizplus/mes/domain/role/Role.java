package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ADMIN, USER, ROLE_001, ...
     */
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
