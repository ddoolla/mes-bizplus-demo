package com.bizplus.mes.domain.routing.process.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoutingProcessCreateDto {

    @NotEmpty
    private List<Long> processIds;
}
