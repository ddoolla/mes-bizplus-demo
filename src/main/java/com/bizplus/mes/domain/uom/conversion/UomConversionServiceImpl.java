package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.conversion.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UomConversionServiceImpl implements UomConversionService {

    private final UomConversionRepository uomConversionRepository;

    private final UomReader uomReader;
    private final UomConversionReader uomConversionReader;

    @Override
    public UomConversionListDto getUomConversions(UomConversionSearchDto dto, Pageable pageable) {
        Page<UomConversionDto> uomConversionPage = uomConversionRepository.findUomConversions(dto, pageable);

        return new UomConversionListDto(
                uomConversionPage.getContent(),
                Pagination.of(uomConversionPage)
        );
    }

    @Override
    public UomConversionDto getUomConversion(Long id) {
        return uomConversionRepository.findUomConversion(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UOM_CONVERSION_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkDuplication(Long fromUomId, Long toUomId) {
        boolean exists = uomConversionRepository.existsByFromUomIdAndToUomId(fromUomId, toUomId);

        return !exists;
    }

    @Override
    public void createUomConversion(UomConversionCreateDto dto) {
        Uom fromUom = uomReader.getById(dto.getFromUomId());
        Uom toUom = uomReader.getById(dto.getToUomId());

        uomConversionRepository.save(
                new UomConversion(fromUom, toUom, dto.getFactor())
        );
    }

    @Transactional
    @Override
    public void updateUomConversion(Long id, UomConversionUpdateDto dto) {
        uomConversionReader.getById(id).update(dto.getFactor());
    }

    @Transactional
    @Override
    public void deleteUomConversions(List<Long> ids) {
        ids.forEach(uomConversionRepository::deleteById); // 물리 삭제
    }
}
