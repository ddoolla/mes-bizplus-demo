package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.LotDto;

public interface LotService {

    Long createLot(Long itemId, String lotNo);

    LotDto getByLotNo(String lotNo);
}
