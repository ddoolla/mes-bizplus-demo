package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionListDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InProcessInspectionServiceImpl implements InProcessInspectionService {

    private final InProcessInspectionRepository inProcessInspectionRepository;

    @Override
    public InProcessInspectionListDto getAll(InProcessInspectionSearchDto dto, Pageable pageable) {
        Page<InProcessInspectionSummaryDto> page = inProcessInspectionRepository.fetchAllSummaries(dto, pageable);

        return new InProcessInspectionListDto(page.getContent(), Pagination.of(page));
    }
}
