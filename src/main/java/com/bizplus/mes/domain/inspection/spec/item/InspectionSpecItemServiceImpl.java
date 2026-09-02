package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.domain.inspection.item.InspectionItem;
import com.bizplus.mes.domain.inspection.item.InspectionItemReader;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemCreateDto;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemDto;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionSpecItemServiceImpl implements InspectionSpecItemService {

    private final InspectionSpecItemRepository inspectionSpecItemRepository;

    private final InspectionItemReader inspectionItemReader;
    private final InspectionSpecReader inspectionSpecReader;
    private final InspectionSpecItemReader inspectionSpecItemReader;

    @Override
    public List<InspectionSpecItemDto> getInspectionSpecItems(Long inspectionSpecId) {
        return inspectionSpecItemRepository.findInspectionSpecItems(inspectionSpecId);
    }

    @Transactional
    @Override
    public void createInspectionSpecItems(Long inspectionSpecId, InspectionSpecItemCreateDto dto) {
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(inspectionSpecId);

        Integer nextSortOrder = inspectionSpecItemRepository.findNextSortOrder(inspectionSpecId);

        for (Long inspectionItemId : dto.getInspectionItemIds()) {
            InspectionItem inspectionItem = inspectionItemReader.getById(inspectionItemId);

            inspectionSpecItemRepository.save(InspectionSpecItemMapper
                    .toEntity(
                            inspectionSpec,
                            inspectionItem,
                            nextSortOrder
                    ));

            nextSortOrder++;
        }
    }

    @Transactional
    @Override
    public void updateInspectionSpecItems(List<InspectionSpecItemUpdateDto> dtoList) {
        dtoList.forEach(dto -> {
            InspectionSpecItem inspectionSpecItem = inspectionSpecItemReader.getById(dto.getId());

            InspectionSpecItemMapper.apply(inspectionSpecItem, dto);
        });

    }

    @Transactional
    @Override
    public void deleteInspectionSpecItems(List<Long> ids) {
        ids.forEach(id -> inspectionSpecItemReader.getById(id).delete());
    }
}
