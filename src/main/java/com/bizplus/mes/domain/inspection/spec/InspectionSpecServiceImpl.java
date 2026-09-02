package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inspection.spec.dto.*;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.process.ProcessReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionSpecServiceImpl implements InspectionSpecService {

    private final InspectionSpecRepository inspectionSpecRepository;

    private final ItemReader itemReader;
    private final ProcessReader processReader;
    private final InspectionSpecReader inspectionSpecReader;

    @Override
    public InspectionSpecListDto getInspectionSpecs(InspectionSpecSearchDto dto, Pageable pageable) {
        Page<InspectionSpecDto> inspectionSpecPage = inspectionSpecRepository.findInspectionSpecs(dto, pageable);

        return new InspectionSpecListDto(
                inspectionSpecPage.getContent(),
                Pagination.of(inspectionSpecPage)
        );
    }

    @Override
    public InspectionSpecDto getInspectionSpec(Long id) {
        return inspectionSpecRepository.findInspectionSpec(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSPECTION_SPEC_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkInspectionSpecCode(Long id, String code) {
        boolean exists = inspectionSpecRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Transactional
    @Override
    public Long createInspectionSpec(InspectionSpecCreateDto dto) {
        Item item = itemReader.getById(dto.getItemId());
        Process process = null;

        // 공정검사 기준일 경우 공정 정보 추가
        if (dto.getType() == InspectionType.PROCESS) {
            process = processReader.getById(dto.getProcessId());
        }

        // 기본 기준으로 생성할 경우 기존 기본 기준 초기화 (같은 검사유형, 제품, 공정에 속하는)
        if (dto.isPrimary()) {
            inspectionSpecRepository.resetPrimary(
                    dto.getItemId(), dto.getProcessId(), dto.getType());
        }

        return inspectionSpecRepository.save(
                        InspectionSpecMapper.toEntity(item, process, dto)
                )
                .getId();
    }

    @Transactional
    @Override
    public void updateInspectionSpec(Long id, InspectionSpecUpdateDto dto) {
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(id);

        // 기본 기준으로 생성할 경우 기존 기본 기준 초기화 (같은 검사유형, 제품, 공정에 속하는)
        if (dto.isPrimary() && !inspectionSpec.isPrimary()) {
            inspectionSpecRepository.resetPrimary(
                    inspectionSpec.getItem().getId(),
                    inspectionSpec.getProcess() != null
                            ? inspectionSpec.getProcess().getId()
                            : null,
                    inspectionSpec.getType()
            );
        }

        InspectionSpecMapper.apply(inspectionSpec, dto);
    }

    @Transactional
    @Override
    public void deleteInspectionSpecs(List<Long> ids) {
        ids.forEach(id -> inspectionSpecReader.getById(id).delete());
    }
}
