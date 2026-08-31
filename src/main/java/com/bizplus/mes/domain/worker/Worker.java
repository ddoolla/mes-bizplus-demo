package com.bizplus.mes.domain.worker;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String code;

    private String remark;

    public Worker(User user, String code, String remark) {
        this.user = user;
        this.code = code;
        this.remark = remark;
    }

    public void update(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }
}
