package com.bizplus.mes.common.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class SoftDeletableEntity extends AuditableEntity {

    private LocalDateTime deletedAt;


    public void restore() {
        this.deletedAt = null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}

// 변경 검토
//@Getter
//@MappedSuperclass
//public abstract class SoftDeletableEntity extends AuditableEntity {
//
//    private LocalDateTime deletedAt;
//
//    /**
//     * 유니크 제약용 보조 컬럼.
//     * 살아 있으면 0, 삭제되면 해당 행의 PK로 변경된다.
//     * 삭제 여부 판단은 deletedAt 기준으로 하고, 이 값은 조회 조건에 쓰지 않는다.
//     */
//    @Column(name = "deleted_key", nullable = false)
//    private Long deletedKey = 0L;
//
//    public abstract Long getId();
//
//    public void restore() {
//        this.deletedAt = null;
//        this.deletedKey = 0L;
//    }
//
//    public void delete() {
//        if (getId() == null) {
//            throw new IllegalStateException("저장되지 않은 엔티티는 삭제할 수 없습니다.");
//        }
//        this.deletedAt = LocalDateTime.now();
//        this.deletedKey = getId();
//    }
//}
