package com.bizplus.mes.domain.inspection.incoming;

import java.time.LocalDate;

public interface IncomingInspectionService {

    /**
     * 입고 시 검사 합격으로 자동생성
     *
     * @param receiptItemId    - 입고 자재 아이디
     * @param inspectionSpecId - 검사기준 아이디
     * @param date             - 입고일
     * @return - 생성된 검사 아이디 (inspection.id)
     */
    Long autoCreate(Long receiptItemId, Long inspectionSpecId, LocalDate date);
}
