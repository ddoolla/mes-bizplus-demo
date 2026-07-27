package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private CodeGroupKey groupKey;

    @Column(nullable = false)
    private String name;

    private Integer sortOrder;

    public CodeGroup(Menu menu, CodeGroupKey groupKey) {
        this.menu = menu;
        this.groupKey = groupKey;
        this.name = groupKey.getName();
        this.sortOrder = groupKey.getSortOrder();
    }

    public void update(Menu menu, CodeGroupKey groupKey) {
        this.menu = menu;
        this.groupKey = groupKey;
        this.name = groupKey.getName();
        this.sortOrder = groupKey.getSortOrder();
    }
}
