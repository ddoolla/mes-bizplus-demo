package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionResultServiceImpl implements InspectionResultService {

    private final InspectionResultRepository inspectionResultRepository;

    @Override
    public List<InspectionResultDto> getAllByInspectionId(Long inspectionId) {
        return inspectionResultRepository.fetchInspectionResults(inspectionId);
    }
}
