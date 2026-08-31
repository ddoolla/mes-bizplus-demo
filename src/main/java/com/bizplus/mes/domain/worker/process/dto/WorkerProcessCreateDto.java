package com.bizplus.mes.domain.worker.process.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkerProcessCreateDto {

    @NotEmpty
    private List<Long> processIds;
}
