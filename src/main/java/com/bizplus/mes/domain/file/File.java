package com.bizplus.mes.domain.file;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(unique = true, nullable = false)
    private String storedName;

    @Column(nullable = false)
    private String storagePath; // 상대경로 저장 (root-path X)

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String contentType; // MIME Type

    @Column(nullable = false)
    private Long size;

    public File(String originalName,
                String storedName,
                String storagePath,
                String extension,
                String contentType,
                Long size) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.storagePath = storagePath;
        this.extension = extension;
        this.contentType = contentType;
        this.size = size;
    }

    public void changeStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
