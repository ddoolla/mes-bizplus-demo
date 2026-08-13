package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.inventory.InventoryValidator;
import com.bizplus.mes.domain.item.dto.*;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final CommonCodeReader commonCodeReader;
    private final UomReader uomReader;
    private final ItemReader itemReader;
    private final InventoryReader inventoryReader;

    private final InventoryValidator inventoryValidator;

    @Override
    public ItemListDto getItems(ItemSearchDto dto, Pageable pageable) {
        Page<ItemDto> itemPage = itemRepository.findItems(dto, pageable);

        return new ItemListDto(
                itemPage.getContent(),
                Pagination.of(itemPage));
    }

    @Override
    public ItemDto getItem(Long id) {
        return itemRepository.findItem(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkCode(Long id, String code) {
        boolean exists = itemRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public boolean checkLotManage(Long id, boolean lotManaged) {
        Item item = itemReader.getById(id);

        if (item.isLotManaged() == lotManaged) {
            return true;
        }

        return !inventoryReader.hasStock(id);
    }

    @Transactional
    @Override
    public void createItem(ItemCreateDto dto) {
        CommonCode itemCategory = commonCodeReader.getOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        itemRepository.save(ItemMapper.toEntity(itemCategory, uom, dto));
    }

    @Transactional
    @Override
    public void updateItem(Long id, ItemUpdateDto dto) {
        Item item = itemReader.getById(id);
        CommonCode itemCategory = commonCodeReader.getOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        // 재고가 없는 경우에만 LOT 관리 여부 변경 가능
        if (item.isLotManaged() != dto.isLotManaged()) {
            inventoryValidator.validateNoStock(id);
            item.updateLotManaged(dto.isLotManaged());
        }

        ItemMapper.apply(item, itemCategory, uom, dto);
    }

    @Transactional
    @Override
    public void deleteItems(List<Long> ids) {
        ids.forEach(id -> itemReader.getById(id).delete());
    }
}
