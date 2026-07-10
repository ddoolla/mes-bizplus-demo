package com.bizplus.mes.domain.menu;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuType type;

    private String path;

    private Integer sortOrder;

    public Menu(Menu parent,
                String code,
                String name,
                MenuType type,
                String path,
                Integer sortOrder) {
        this.parent = parent;
        this.code = code;
        this.name = name;
        this.type = type;
        this.path = path;
        this.sortOrder = sortOrder;
    }
}
