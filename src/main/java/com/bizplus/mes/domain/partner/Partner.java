package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "partners")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Partner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private PartnerType type;

    private String businessNo;
    private String corporateNo;
    private String ceoName;

    private String tel;
    private String fax;

    private String email;
    private String homePage;

    private String zipCode;
    private String address;
    private String addressDetail;

    private String remark;

    public Partner(String code,
                   String name,
                   PartnerType type,
                   String businessNo,
                   String corporateNo,
                   String ceoName,
                   String tel,
                   String fax,
                   String email,
                   String homePage,
                   String zipCode,
                   String address,
                   String addressDetail,
                   String remark) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.businessNo = businessNo;
        this.corporateNo = corporateNo;
        this.ceoName = ceoName;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.homePage = homePage;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.remark = remark;
    }

    public void update(String code,
                       String name,
                       PartnerType type,
                       String businessNo,
                       String corporateNo,
                       String ceoName,
                       String tel,
                       String fax,
                       String email,
                       String homePage,
                       String zipCode,
                       String address,
                       String addressDetail,
                       String remark) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.businessNo = businessNo;
        this.corporateNo = corporateNo;
        this.ceoName = ceoName;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.homePage = homePage;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.remark = remark;
    }
}
