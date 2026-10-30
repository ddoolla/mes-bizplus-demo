package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.item.dto.ItemCreateDto;
import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemListDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
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

    @Override
    public ItemListDto getItems(ItemSearchDto dto, Pageable pageable) {
        Page<ItemDto> itemPage = itemRepository.fetchItems(dto, pageable);

        return new ItemListDto(itemPage.getContent(), Pagination.of(itemPage));
    }

    @Override
    public ItemDto getItem(Long id) {
        return itemRepository.fetchItem(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkCode(Long id, String code) {
        boolean exists = itemRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Transactional
    @Override
    public Long createItem(ItemCreateDto dto) {
        CommonCode itemCategory = commonCodeReader.getByIdOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        return itemRepository.save(ItemMapper.toEntity(itemCategory, uom, dto)).getId();
    }

    @Transactional
    @Override
    public void deleteItems(List<Long> ids) {
        ids.forEach(id -> itemReader.getById(id).delete());
    }
}
