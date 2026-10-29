package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.LotCreateDto;
import com.bizplus.mes.domain.lot.dto.LotDto;

public interface LotService {

    Long create(LotCreateDto dto);

    LotDto getByLotNo(String lotNo);
}
