package com.bizplus.mes.domain.lot;

import java.time.LocalDate;

public interface LotQueryRepository {

    String findMaxLotNo(LocalDate date);
}
