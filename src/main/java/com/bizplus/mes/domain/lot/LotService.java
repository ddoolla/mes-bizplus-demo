package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.LotCreateDto;
import com.bizplus.mes.domain.lot.dto.LotDto;
import com.bizplus.mes.domain.lot.dto.LotListDto;
import com.bizplus.mes.domain.lot.dto.LotSearchDto;
import org.springframework.data.domain.Pageable;

public interface LotService {

    LotListDto getAll(LotSearchDto dto, Pageable pageable);

    LotDto getById(Long id);

    LotDto getByLotNo(String lotNo);

    Long create(LotCreateDto dto);
}
