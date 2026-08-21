package com.bizplus.mes.domain.user.role;

public interface UserRoleService {

    void createUserRole(Long userId, Long roleId);

    void updateUserRole(Long userId, Long roleId);
}
