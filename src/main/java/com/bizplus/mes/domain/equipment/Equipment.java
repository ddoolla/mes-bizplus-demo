package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.code.common.CommonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Equipment extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_code_id")
    private CommonCode type;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String specification;

    private String manufacturer;

    private String model;

    private String serialNo;

    private String location;

    private String remark;

    /*
    * 데이터 수집 프로그램 설정 파일의 senderId 값
    * 현재는 테이블에 직접 매핑하고 있음
    * */
    @Column(
            unique = true,
            comment = "데이터 수집 프로그램 설정 파일의 senderId 값"
    )
    private Long senderId;

    public Equipment(CommonCode type,
                     String code,
                     String name,
                     String specification,
                     String manufacturer,
                     String model,
                     String serialNo,
                     String location,
                     String remark) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.specification = specification;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNo = serialNo;
        this.location = location;
        this.remark = remark;
    }

    public void update(CommonCode type,
                       String code,
                       String name,
                       String specification,
                       String manufacturer,
                       String model,
                       String serialNo,
                       String location,
                       String remark) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.specification = specification;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNo = serialNo;
        this.location = location;
        this.remark = remark;
    }
}
