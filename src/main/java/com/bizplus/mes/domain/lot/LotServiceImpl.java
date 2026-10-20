package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.dto.LotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;
    private final InventoryReader inventoryReader;

    @Override
    public Long createLot(Long itemId, String lotNo) {
        Item item = itemReader.getById(itemId);

        if (!item.isLotManaged()) {
            throw new IllegalStateException("LOT 관리 품목이 아닙니다. itemId: " + item);
        }

        return lotRepository.save(new Lot(item, lotNo)).getId();
    }

    @Transactional
    @Override
    public LotDto getByLotNo(String lotNo) {
        Lot lot = lotReader.getByLotNo(lotNo);
        Item item = lot.getItem();

        Inventory inventory = inventoryReader.get(item.getId(), lot.getId());

        return new LotDto(
                lot.getId(),
                lot.getNo(),
                item.getId(),
                item.getCode(),
                item.getName(),
                item.getType(),
                item.getSpecification(),
                inventory.getId(),
                inventory.getQuantity()
        );
    }
}
