package com.bizplus.mes.domain.permission;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionAction action;

    public Permission(Menu menu,
                      String code,
                      String name,
                      PermissionAction action) {
        this.menu = menu;
        this.code = code;
        this.name = name;
        this.action = action;
    }

    public void update(Menu menu,
                       String code,
                       String name,
                       PermissionAction action) {
        this.menu = menu;
        this.code = code;
        this.name = name;
        this.action = action;
    }
}
