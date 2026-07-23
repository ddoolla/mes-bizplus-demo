package com.bizplus.mes.domain.log.auth.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserAuthLogListDto {

    private List<UserAuthLogDto> userAuthLogs;
    private Pagination pagination;
}
