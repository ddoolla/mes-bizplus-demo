package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.file.File;
import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFile extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private Integer sortOrder;

    public ItemFile(Item item,
                    File file,
                    FileType type,
                    Integer sortOrder) {
        this.item = item;
        this.file = file;
        this.type = type;
        this.sortOrder = sortOrder;
    }

    public void updateFile(File file) {
        this.file = file;
    }
}
