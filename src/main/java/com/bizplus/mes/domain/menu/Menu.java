package com.bizplus.mes.domain.menu;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private MenuCode code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private MenuType type;

    private String path;

    private Integer sortOrder;

    public Menu(Menu parent, MenuCode code) {
        this.parent = parent;
        this.code = code;
        this.name = code.getName();
        this.type = code.getType();
        this.path = code.getPath();
        this.sortOrder = code.getSortOrder();
    }

    public void update(Menu parent, MenuCode code) {
        this.parent = parent;
        this.code = code;
        this.name = code.getName();
        this.type = code.getType();
        this.path = code.getPath();
        this.sortOrder = code.getSortOrder();
    }
}
