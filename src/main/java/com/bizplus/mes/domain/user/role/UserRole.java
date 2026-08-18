package com.bizplus.mes.domain.user.role;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 확장성을 위해 중간 테이블을 두었지만, 현재는 사용자별 1개의 역할만 부여.
* 물리 삭제 데이터
* */
@Entity
@Table(name = "user_roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}
