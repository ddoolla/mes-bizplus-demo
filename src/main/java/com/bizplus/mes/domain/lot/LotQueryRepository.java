package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.LotDto;
import com.bizplus.mes.domain.lot.dto.LotSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface LotQueryRepository {

    Page<LotDto> fetchAll(LotSearchDto dto, Pageable pageable);

    Page<LotDto> fetchAllByItemId(Long itemId, LotSearchDto dto, Pageable pageable);

    Optional<LotDto> fetchById(Long id);

    Optional<LotDto> fetchByLotNo(String lotNo);

    String findMaxLotNo(LocalDate date);
}
