package com.bizplus.mes.domain.role.permission;

import com.bizplus.mes.domain.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends
        JpaRepository<RolePermission, Long> {

    boolean existsByPermission(Permission permission);
}
