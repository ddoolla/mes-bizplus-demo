package com.bizplus.mes.domain.log.action.dto;

import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserActionLogSearchDto {

    private String userId;
    private MenuCode menuCode;
    private ActionType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
