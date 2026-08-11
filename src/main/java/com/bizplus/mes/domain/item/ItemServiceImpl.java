package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.inventory.InventoryService;
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

    private final InventoryService inventoryService;

    private final CommonCodeReader commonCodeReader;
    private final UomReader uomReader;
    private final ItemReader itemReader;

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

    @Transactional
    @Override
    public void createItem(ItemCreateDto dto) {

        CommonCode itemCategory = commonCodeReader.getOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        Item newItem = itemRepository.save(ItemMapper.toEntity(itemCategory, uom, dto));

        inventoryService.createInventory(newItem.getId());
    }

    @Transactional
    @Override
    public void updateItem(Long id, ItemUpdateDto dto) {

        Item item = itemReader.getById(id);
        CommonCode itemCategory = commonCodeReader.getOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        ItemMapper.apply(item, itemCategory, uom, dto);
    }

    @Transactional
    @Override
    public void deleteItems(List<Long> ids) {

        ids.forEach(id -> itemReader.getById(id).delete());
    }
}
