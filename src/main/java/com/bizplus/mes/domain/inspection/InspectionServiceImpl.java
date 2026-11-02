package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final InspectionRepository inspectionRepository;

    @Override
    public InspectionDto getById(Long id) {
        return inspectionRepository.fetchById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INSPECTION_NOT_FOUND,
                        "inspection id: %d".formatted(id)
                ));
    }
}
