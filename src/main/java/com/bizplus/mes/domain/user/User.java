package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_code_id")
    private CommonCode department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_code_id")
    private CommonCode position;


    public User(String userId,
                String password,
                String name,
                String email,
                String phone,
                CommonCode department,
                CommonCode position) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
    }

    public void update(String name,
                       String email,
                       String phone,
                       CommonCode department,
                       CommonCode position) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
    }
}
