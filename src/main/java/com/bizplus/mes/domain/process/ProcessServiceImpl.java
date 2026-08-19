package com.bizplus.mes.domain.process;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.process.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;

    private final ProcessReader processReader;

    @Override
    public ProcessListDto getProcesses(ProcessSearchDto dto, Pageable pageable) {
        Page<ProcessDto> processPage = processRepository.findProcesses(dto, pageable)
                .map(ProcessMapper::toDto);

        return new ProcessListDto(processPage.getContent(), Pagination.of(processPage));
    }

    @Override
    public ProcessDto getProcess(Long id) {
        return ProcessMapper.toDto(processReader.getById(id));
    }

    @Override
    public boolean checkProcessCode(Long id, String code) {
        boolean exists = processRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createProcess(ProcessCreateDto dto) {
        processRepository.save(ProcessMapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void updateProcess(Long id, ProcessUpdateDto dto) {
        Process process = processReader.getById(id);
        ProcessMapper.apply(process, dto);
    }

    @Transactional
    @Override
    public void deleteProcesses(List<Long> ids) {
        ids.forEach(id -> processReader.getById(id).delete());
    }
}
