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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BomItemServiceImpl implements BomItemService {

    private final BomItemRepository bomItemRepository;

    private final BomReader bomReader;
    private final ItemReader itemReader;
    private final UomReader uomReader;


    @Override
    public List<BomItemDto> getBomItems(Long bomId) {
        return bomItemRepository.findBomItems(bomId);
    }

    @Transactional
    @Override
    public void createBomItems(Long bomId, List<BomItemCreateDto> dtos) {
        Bom bom = bomReader.getById(bomId);

        dtos.forEach(dto -> {
            Item item = itemReader.getById(dto.getItemId());
            Uom uom = uomReader.getById(dto.getUomId());

            bomItemRepository.save(BomItemMapper.toEntity(bom, item, uom, dto));
        });
    }

    @Override
    public void updateBomItems(List<BomItemUpdateDto> dtos) {

    }
}
