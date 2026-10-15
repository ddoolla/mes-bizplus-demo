package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    private final ItemReader itemReader;

    @Override
    public Long createLot(Long itemId, String lotNo) {
        Item item = itemReader.getById(itemId);

        if (!item.isLotManaged()) {
            throw new IllegalStateException("LOT 관리 품목이 아닙니다. itemId: " + item);
        }

        return lotRepository.save(new Lot(item, lotNo)).getId();
    }
}
