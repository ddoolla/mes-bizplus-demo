package com.bizplus.mes.domain.process;

import com.bizplus.mes.domain.process.dto.ProcessSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessQueryRepository {

    Page<Process> findProcesses(ProcessSearchDto dto, Pageable pageable);
}
