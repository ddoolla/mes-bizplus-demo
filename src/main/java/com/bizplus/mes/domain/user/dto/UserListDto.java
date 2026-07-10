package com.bizplus.mes.domain.user.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListDto {

    private List<UserDto> users;
    private Pagination pagination;
}
