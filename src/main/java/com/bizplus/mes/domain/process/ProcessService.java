package com.bizplus.mes.domain.process;

import com.bizplus.mes.domain.process.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProcessService {

    ProcessListDto getProcesses(ProcessSearchDto dto, Pageable pageable);

    ProcessDto getProcess(Long id);

    boolean checkProcessCode(Long id, String code);

    void createProcess(ProcessCreateDto dto);

    void updateProcess(Long id, ProcessUpdateDto dto);

    void deleteProcesses(List<Long> ids);
}
