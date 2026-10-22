package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inspection.InspectionType;
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
import java.util.Objects;

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
        if (dto.getType() == InspectionType.IN_PROCESS) {
            process = processReader.getById(dto.getProcessId());
        }

        // 검사기준 생성
        InspectionSpec newInspectionSpec = inspectionSpecRepository.save(
                InspectionSpecMapper.toEntity(item, process, dto)
        );

        // 기본 검사기준일 경우
        boolean isDefault = Boolean.TRUE.equals(dto.getIsDefault());

        if (isDefault) {
            switch (dto.getType()) {
                case INCOMING -> item.updateDefaultIqcSpec(newInspectionSpec);
                case OUTGOING -> item.updateDefaultOqcSpec(newInspectionSpec);
            }
        }

        return newInspectionSpec.getId();
    }

    @Transactional
    @Override
    public void updateInspectionSpec(Long id, InspectionSpecUpdateDto dto) {
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(id);
        Item item = inspectionSpec.getItem();

        boolean isDefault = Boolean.TRUE.equals(dto.getIsDefault());

        switch (inspectionSpec.getType()) {
            case INCOMING -> {
                if (isDefault) {
                    item.updateDefaultIqcSpec(inspectionSpec);

                } else if (item.getDefaultIqcSpec() != null
                        && Objects.equals(item.getDefaultIqcSpec().getId(), id)) {
                    item.updateDefaultIqcSpec(null);
                }
            }
            case OUTGOING -> {
                if (isDefault) {
                    item.updateDefaultOqcSpec(inspectionSpec);

                } else if (item.getDefaultOqcSpec() != null
                        && Objects.equals(item.getDefaultOqcSpec().getId(), id)) {
                    item.updateDefaultOqcSpec(null);
                }
            }
        }

        InspectionSpecMapper.apply(inspectionSpec, dto);
    }

    @Transactional
    @Override
    public void deleteInspectionSpecs(List<Long> ids) {
        ids.forEach(id -> inspectionSpecReader.getById(id).delete());
    }
}
