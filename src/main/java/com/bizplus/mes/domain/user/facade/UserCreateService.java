package com.bizplus.mes.domain.user.facade;

import com.bizplus.mes.domain.user.UserService;
import com.bizplus.mes.domain.user.dto.UserCreateDto;
import com.bizplus.mes.domain.user.role.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCreateService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Transactional
    public void create(UserCreateDto dto) {
        Long newUserId = userService.createUser(dto);
        userRoleService.createUserRole(newUserId, dto.getRoleId());
    }
}
