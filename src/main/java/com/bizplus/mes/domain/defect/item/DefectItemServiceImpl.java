package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.defect.item.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectItemServiceImpl implements DefectItemService {

    private final DefectItemRepository defectItemRepository;

    private final CommonCodeReader commonCodeReader;
    private final DefectItemReader defectItemReader;

    @Override
    public DefectItemListDto getDefectItems(DefectItemSearchDto dto, Pageable pageable) {
        Page<DefectItemDto> defectItemPage = defectItemRepository.findDefectItems(dto, pageable);

        return new DefectItemListDto(defectItemPage.getContent(), Pagination.of(defectItemPage));
    }

    @Override
    public DefectItemDto getDefectItem(Long id) {
        return defectItemRepository.findDefectItem(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEFECT_ITEM_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkDefectItemCode(Long id, String code) {
        boolean exists = defectItemRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createDefectItem(DefectItemCreateDto dto) {
        CommonCode defectTypeCode = commonCodeReader.getOrNull(dto.getTypeId());

        defectItemRepository.save(DefectItemMapper.toEntity(defectTypeCode, dto));
    }

    @Transactional
    @Override
    public void updateDefectItem(Long id, DefectItemUpdateDto dto) {
        DefectItem defectItem = defectItemReader.getById(id);
        CommonCode defectTypeCode = commonCodeReader.getOrNull(dto.getTypeId());

        DefectItemMapper.apply(defectItem, defectTypeCode, dto);
    }

    @Transactional
    @Override
    public void deleteDefectItems(List<Long> ids) {
        ids.forEach(id -> defectItemReader.getById(id).delete());
    }
}
