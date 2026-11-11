package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.*;
import org.springframework.data.domain.Pageable;

public interface LotService {

    LotListDto getAll(LotSearchDto dto, Pageable pageable);

    LotListDto getAllByItemId(Long itemId, LotSearchDto dto, Pageable pageable);

    LotDto getById(Long id);

    LotDto getByLotNo(String lotNo);

    Long create(LotCreateDto dto);

    void update(Long id, LotUpdateDto dto);
}
