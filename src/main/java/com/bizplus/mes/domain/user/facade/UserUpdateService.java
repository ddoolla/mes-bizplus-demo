package com.bizplus.mes.domain.user.facade;

import com.bizplus.mes.domain.user.UserService;
import com.bizplus.mes.domain.user.dto.UserUpdateDto;
import com.bizplus.mes.domain.user.role.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Transactional
    public void update(Long userId, UserUpdateDto dto) {
        userService.updateUser(userId, dto);
        userRoleService.updateUserRole(userId, dto.getRoleId());
    }
}
