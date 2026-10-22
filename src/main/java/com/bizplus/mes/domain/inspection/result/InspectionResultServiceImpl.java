package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.InspectionReader;
import com.bizplus.mes.domain.inspection.item.InspectionItem;
import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;
import com.bizplus.mes.domain.inspection.spec.item.InspectionSpecItem;
import com.bizplus.mes.domain.inspection.spec.item.InspectionSpecItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionResultServiceImpl implements InspectionResultService {

    private final InspectionResultRepository inspectionResultRepository;

    private final InspectionSpecItemReader inspectionSpecItemReader;
    private final InspectionReader inspectionReader;

    @Override
    public List<InspectionResultDto> getAll(Long inspectionId) {
        return inspectionResultRepository.fetchInspectionResults(inspectionId);
    }

    @Transactional
    @Override
    public void createAll(Long inspectionId, Long inspectionSpecId) {
        Inspection inspection = inspectionReader.getById(inspectionId);
        List<InspectionSpecItem> inspectionSpecItems = inspectionSpecItemReader.getAll(inspectionSpecId);

        inspectionSpecItems.forEach(inspectionSpecItem -> {
            InspectionItem inspectionItem = inspectionSpecItem.getInspectionItem();
            CommonCode inspectionItemGroup = inspectionItem.getGroup();

            // 결과 합격으로 기본 생성
            inspectionResultRepository.save(new InspectionResult(
                    inspection,
                    inspectionSpecItem,
                    inspectionItem.getId(),
                    inspectionItemGroup != null
                            ? inspectionItemGroup.getName()
                            : null,
                    inspectionItem.getCode(),
                    inspectionItem.getName(),
                    inspectionSpecItem.getStandard(),
                    inspectionSpecItem.getMethod(),
                    inspectionSpecItem.getRemark(),
                    inspectionSpecItem.getSortOrder(),
                    null,
                    InspectionJudgement.PASS,
                    null
            ));
        });
    }
}
