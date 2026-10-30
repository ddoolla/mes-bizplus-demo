package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.bom.BomReader;
import com.bizplus.mes.domain.bom.item.dto.BomItemCreateDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemEditDto;
import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.UomValidator;
import com.bizplus.mes.domain.uom.conversion.UomConversionReader;
import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BomItemServiceImpl implements BomItemService {

    private final BomItemRepository bomItemRepository;

    private final UomReader uomReader;
    private final UomConversionReader uomConversionReader;
    private final ItemReader itemReader;
    private final BomReader bomReader;
    private final BomItemReader bomItemReader;

    private final UomValidator uomValidator;

    @Override
    public List<BomItemDto> getBomItems(Long bomId) {
        return bomItemRepository.findBomItems(bomId);
    }

    @Override
    public List<BomItemEditDto> getAllForEdit(Long bomId) {
        return bomItemRepository.findBomItems(bomId).stream()
                .map(bomItemDto -> {
                    List<ConvertibleUomDto> convertibleUoms = uomConversionReader
                            .getConvertibleUoms(bomItemDto.getItem().uom().id());

                    return new BomItemEditDto(
                            bomItemDto,
                            convertibleUoms
                    );
                })
                .toList();
    }

    @Transactional
    @Override
    public void createBomItems(Long bomId, BomItemCreateDto dto) {
        Bom bom = bomReader.getById(bomId);

        dto.getItemIds().stream().distinct().forEach(id -> {
            BomItem existing = bomItemRepository.findIncludingDeleted(bomId, id).orElse(null);

            // 이미 등록된 구성품이면 패스
            if (existing != null && existing.getDeletedAt() == null) {
                return;
            }

            Item item = itemReader.getById(id);

            // 구성품 유형 검증
            validateItemType(bom, item);

            // 순환 참조 검증
            validateCircularReference(bom, item);

            Uom uom = item.getUom();

            if (existing != null) {
                existing.restore();
                existing.update(uom, null);

            } else {
                bomItemRepository.save(BomItemMapper.toEntity(bom, item, uom));
            }
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
        ids.forEach(id -> bomItemReader.getById(id).delete());
    }

    private void validateItemType(Bom bom, Item componentItem) {
        ItemType parentType = bom.getItem().getType();
        ItemType componentType = componentItem.getType();

        ItemGroup group = switch (parentType) {
            case FINISHED_GOODS -> ItemGroup.FINISHED_GOODS_BOM_ITEM;
            case SEMI_FINISHED_GOODS -> ItemGroup.SEMI_FINISHED_GOODS_BOM_ITEM;
            default -> throw new BusinessException("해당 품목 유형은 BOM을 구성할 수 없습니다.");
        };

        if (!group.getTypes().contains(componentType)) {
            throw new BusinessException("선택한 품목은 해당 BOM의 구성품으로 등록할 수 없습니다.");
        }
    }

    private void validateCircularReference(Bom bom, Item componentItem) {
        Long parentItemId = bom.getItem().getId();
        Long componentItemId = componentItem.getId();

        if (parentItemId.equals(componentItemId)) {
            throw new BusinessException("모품목과 동일한 품목은 구성품으로 등록할 수 없습니다.");
        }

        if (containsParentItem(parentItemId, componentItemId, new HashSet<>())) {
            throw new BusinessException("BOM 구성 관계가 중복되어 등록할 수 없습니다.");
        }
    }

    private boolean containsParentItem(Long parentItemId, Long componentItemId, Set<Long> visited) {
        if (componentItemId.equals(parentItemId)) {
            return true;
        }

        if (!visited.add(componentItemId)) {
            return false;
        }

        List<Long> componentIds = bomItemRepository.findComponentItemIds(componentItemId);

        for (Long childItemId : componentIds) {
            if (containsParentItem(childItemId, parentItemId, visited)) {
                return true;
            }
        }

        return false;
    }
}
