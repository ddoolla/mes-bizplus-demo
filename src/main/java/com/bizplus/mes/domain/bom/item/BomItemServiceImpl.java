package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.bom.BomReader;
import com.bizplus.mes.domain.bom.item.dto.BomItemCreateDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.UomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BomItemServiceImpl implements BomItemService {

    private final BomItemRepository bomItemRepository;

    private final ItemReader itemReader;
    private final BomReader bomReader;
    private final BomItemReader bomItemReader;
    private final UomReader uomReader;

    private final UomValidator uomValidator;

    @Override
    public List<BomItemDto> getBomItems(Long bomId) {
        return bomItemRepository.findBomItems(bomId);
    }

    @Transactional
    @Override
    public void createBomItems(Long bomId, BomItemCreateDto dto) {
        Bom bom = bomReader.getById(bomId);

        dto.getItemIds().forEach(id -> {
            if (bomItemRepository.existsByBomIdAndItemId(bomId, id)) {
                return;
            }

            Item item = itemReader.getById(id);
            Uom uom = item.getUom();

            bomItemRepository.save(BomItemMapper.toEntity(bom, item, uom));
        });
    }

    @Transactional
    @Override
    public void updateBomItems(List<BomItemUpdateDto> dtos) {
        dtos.forEach(dto -> {
            BomItem bomItem = bomItemReader.getById(dto.getId());
            Uom uom = uomReader.getById(dto.getUomId());

            uomValidator.validateQuantity(dto.getQuantity(), uom);

            BomItemMapper.apply(bomItem, uom, dto);
        });
    }

    @Transactional
    @Override
    public void deleteBomItems(List<Long> ids) {
        ids.forEach(bomItemRepository::deleteById);
    }
}
