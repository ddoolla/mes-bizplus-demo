package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;

    @Override
    public LotListDto getAll(LotSearchDto dto, Pageable pageable) {
        Page<LotDto> page = lotRepository.fetchAll(dto, pageable);

        return new LotListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public LotDto getById(Long id) {
        return lotRepository.fetchById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LOT_NOT_FOUND,
                        "lot id: %s".formatted(id)
                ));
    }

    @Transactional
    @Override
    public LotDto getByLotNo(String lotNo) {
        return lotRepository.fetchByLotNo(lotNo)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LOT_NOT_FOUND,
                        "lot number: %s".formatted(lotNo)
                ));
    }

    @Override
    public Long create(LotCreateDto dto) {
        Item item = itemReader.getById(dto.getItemId());

        if (!item.isLotManaged()) {
            throw new IllegalStateException("LOT 관리 품목이 아닙니다. itemId: " + item);
        }

        return lotRepository.save(new Lot(
                        item,
                        dto.getNo(),
                        dto.getManufactureDate(),
                        dto.getExpirationDate(),
                        null
                ))
                .getId();
    }

    @Transactional
    @Override
    public void update(Long id, LotUpdateDto dto) {
        Lot lot = lotReader.getById(id);

        lot.update(dto.getExpirationDate(), dto.getRemark());
    }
}
