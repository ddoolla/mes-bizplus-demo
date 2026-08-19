package com.bizplus.mes.domain.process;

import com.bizplus.mes.domain.process.dto.ProcessCreateDto;
import com.bizplus.mes.domain.process.dto.ProcessDto;
import com.bizplus.mes.domain.process.dto.ProcessUpdateDto;

public class ProcessMapper {

    public static ProcessDto toDto(Process process) {
        return new ProcessDto(
                process.getId(),
                process.getCode(),
                process.getName(),
                process.getDescription()
        );
    }

    public static Process toEntity(ProcessCreateDto dto) {
        return new Process(
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }

    public static void apply(Process process, ProcessUpdateDto dto) {
        process.update(
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }
}
