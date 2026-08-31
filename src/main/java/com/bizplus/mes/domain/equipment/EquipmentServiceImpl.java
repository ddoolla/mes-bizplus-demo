package com.bizplus.mes.domain.equipment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.equipment.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final CommonCodeReader commonCodeReader;
    private final EquipmentReader equipmentReader;

    @Override
    public EquipmentListDto getEquipments(EquipmentSearchDto dto, Pageable pageable) {
        Page<EquipmentDto> equipmentPage = equipmentRepository.findEquipments(dto, pageable);

        return new EquipmentListDto(equipmentPage.getContent(), Pagination.of(equipmentPage));
    }

    @Override
    public EquipmentDto getEquipment(Long id) {
        return equipmentRepository.findEquipment(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EQUIPMENT_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkEquipmentCode(Long id, String code) {
        boolean exists = equipmentRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createEquipment(EquipmentCreateDto dto) {
        CommonCode equipmentTypeCode = commonCodeReader.getOrNull(dto.getTypeId());

        equipmentRepository.save(EquipmentMapper.toEntity(equipmentTypeCode, dto));
    }

    @Transactional
    @Override
    public void updateEquipment(Long id, EquipmentUpdateDto dto) {
        Equipment equipment = equipmentReader.getById(id);
        CommonCode equipmentTypeCode = commonCodeReader.getOrNull(dto.getTypeId());

        EquipmentMapper.apply(equipment, equipmentTypeCode, dto);
    }

    @Transactional
    @Override
    public void deleteEquipments(List<Long> ids) {
        ids.forEach(id -> equipmentReader.getById(id).delete());
    }
}
