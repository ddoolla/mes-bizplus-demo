package com.bizplus.mes.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일 정보를 찾을 수 없습니다."),

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),

    PERMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "권한을 찾을 수 없습니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "역할을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 권한을 찾을 수 없습니다."),

    CODE_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "공통코드 그룹을 찾을 수 없습니다."),
    COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "공통코드를 찾을 수 없습니다."),
    UOM_NOT_FOUND(HttpStatus.NOT_FOUND, "단위를 찾을 수 없습니다."),
    UOM_CONVERSION_NOT_FOUND(HttpStatus.NOT_FOUND, "단위 환산 정보를 찾을 수 없습니다."),
    PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, "거래처를 찾을 수 없습니다."),
    PARTNER_CONTACT_NOT_FOUND(HttpStatus.NOT_FOUND, "거래처 담당자를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "품목을 찾을 수 없습니다."),
    BOM_NOT_FOUND(HttpStatus.NOT_FOUND, "BOM을 찾을 수 없습니다."),
    BOM_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "BOM 구성 품목을 찾을 수 없습니다."),
    PROCESS_NOT_FOUND(HttpStatus.NOT_FOUND, "공정을 찾을 수 없습니다."),
    ROUTING_NOT_FOUND(HttpStatus.NOT_FOUND, "제품 공정을 찾을 수 없습니다."),
    ROUTING_PROCESS_NOT_FOUND(HttpStatus.NOT_FOUND, "제품 공정 단계를 찾을 수 없습니다."),
    PROCESS_MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "공정 소모 자재를 찾을 수 없습니다."),
    EQUIPMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "설비를 찾을 수 없습니다."),
    WORKER_NOT_FOUND(HttpStatus.NOT_FOUND, "작업자를 찾을 수 없습니다."),
    INSPECTION_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "검사 항목을 찾을 수 없습니다."),
    INSPECTION_SPEC_NOT_FOUND(HttpStatus.NOT_FOUND, "검사 기준을 찾을 수 없습니다."),
    INSPECTION_SPEC_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "검사 기준 항목을 찾을 수 없습니다."),
    DEFECT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "불량 항목을 찾을 수 없습니다."),

    INVALID_PERMISSION(HttpStatus.BAD_REQUEST, "잘못된 권한 정보입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    UOM_QUANTITY_SCALE_EXCEEDED(HttpStatus.BAD_REQUEST, "단위의 허용 소수점 자릿수를 초과했습니다."),
    ITEM_HAS_STOCK(HttpStatus.BAD_REQUEST, "재고가 있는 품목은 LOT 관리 여부를 변경할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
