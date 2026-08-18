package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_code_id")
    private CommonCode department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_code_id")
    private CommonCode position;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String email;

    private String phone;

    private String remark;

    public User(CommonCode department,
                CommonCode position,
                String userId,
                String password,
                String name,
                String email,
                String phone,
                String remark) {
        this.department = department;
        this.position = position;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.remark = remark;
    }

    public void update(CommonCode department,
                       CommonCode position,
                       String name,
                       String email,
                       String phone,
                       String remark) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.remark = remark;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
