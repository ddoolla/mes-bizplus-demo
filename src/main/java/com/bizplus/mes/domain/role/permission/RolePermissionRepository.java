package com.bizplus.mes.domain.role.permission;

import com.bizplus.mes.domain.permission.Permission;
import com.bizplus.mes.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends
        JpaRepository<RolePermission, Long> {

    List<RolePermission> findAllByRole(Role role);

    boolean existsByPermission(Permission permission);

    void deleteAllByRole(Role role);
}
