package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer sortOrder;

    public CodeGroup(Menu menu,
                     String code,
                     String name,
                     String description,
                     Integer sortOrder) {
        this.menu = menu;
        this.code = code;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
