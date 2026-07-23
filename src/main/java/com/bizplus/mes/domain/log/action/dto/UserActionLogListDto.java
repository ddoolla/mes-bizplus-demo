package com.bizplus.mes.domain.log.action.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserActionLogListDto {

    private List<UserActionLogDto> userActionLogs;
    private Pagination pagination;
}
