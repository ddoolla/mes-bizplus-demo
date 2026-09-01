package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.inspection.item.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionItemServiceImpl implements InspectionItemService {

    private final InspectionItemRepository inspectionItemRepository;

    private final CommonCodeReader commonCodeReader;
    private final InspectionItemReader inspectionItemReader;

    @Override
    public InspectionItemListDto getInspectionItems(InspectionItemSearchDto dto, Pageable pageable) {
        Page<InspectionItemDto> inspectionItemPage = inspectionItemRepository.findInspectionItems(dto, pageable);

        return new InspectionItemListDto(inspectionItemPage.getContent(), Pagination.of(inspectionItemPage));
    }

    @Override
    public InspectionItemDto getInspectionItem(Long id) {
        return inspectionItemRepository.findInspectionItem(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_ITEM_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkInspectionItemCode(Long id, String code) {
        boolean exists = inspectionItemRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createInspectionItem(InspectionItemCreateDto dto) {
        CommonCode groupCode = commonCodeReader.getOrNull(dto.getGroupId());

        inspectionItemRepository.save(InspectionItemMapper.toEntity(groupCode, dto));
    }

    @Transactional
    @Override
    public void updateInspectionItem(Long id, InspectionItemUpdateDto dto) {
        InspectionItem inspectionItem = inspectionItemReader.getById(id);
        CommonCode groupCode = commonCodeReader.getOrNull(dto.getGroupId());

        InspectionItemMapper.apply(inspectionItem, groupCode, dto);
    }

    @Transactional
    @Override
    public void deleteInspectionItems(List<Long> ids) {
        ids.forEach(id -> inspectionItemReader.getById(id).delete());
    }
}
