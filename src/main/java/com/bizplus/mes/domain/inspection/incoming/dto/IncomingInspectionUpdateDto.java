package com.bizplus.mes.domain.inspection.incoming.dto;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultUpdateDto;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class IncomingInspectionUpdateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Positive
    private BigDecimal inspectedQuantity;
    private String remark;

    private List<InspectionResultUpdateDto> inspectionResults;
}
