package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.common.entity.BaseEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.partner.Partner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partner_contacts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnerContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_code_id")
    private CommonCode department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_code_id")
    private CommonCode position;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String tel;
    private String email;

    private String remark;

//    @Column(nullable = false)
//    private boolean main; // 보류

    @Column(nullable = false)
    private boolean active = true;

    public PartnerContact(Partner partner,
                          CommonCode department,
                          CommonCode position,
                          String name,
                          String phone,
                          String tel,
                          String email,
                          String remark) {
        this.partner = partner;
        this.department = department;
        this.position = position;
        this.name = name;
        this.phone = phone;
        this.tel = tel;
        this.email = email;
        this.remark = remark;
    }

    public void update(CommonCode department,
                       CommonCode position,
                       String name,
                       String phone,
                       String tel,
                       String email,
                       String remark,
                       boolean active) {
        this.department = department;
        this.position = position;
        this.name = name;
        this.phone = phone;
        this.tel = tel;
        this.email = email;
        this.remark = remark;
        this.active = active;
    }
}
