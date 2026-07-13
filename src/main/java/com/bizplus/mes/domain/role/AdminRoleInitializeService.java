package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.permission.Permission;
import com.bizplus.mes.domain.permission.PermissionRepository;
import com.bizplus.mes.domain.role.permission.RolePermission;
import com.bizplus.mes.domain.role.permission.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRoleInitializeService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Value("${app.admin.role.code}")
    private String roleCode;

    @Value("${app.admin.role.name}")
    private String roleName;

    public void initialize() {

        Role admin = roleRepository.findByCode(roleCode).orElse(null);

        if (admin == null) {

            admin = roleRepository.save(new Role(roleCode, roleName, null));
        }

        List<Permission> permissions = permissionRepository.findAll();

        for (Permission permission : permissions) {

            boolean exists = rolePermissionRepository.existsByPermission(permission);

            if (exists) {

                continue;
            }

            rolePermissionRepository.save(new RolePermission(admin, permission));
        }
    }
}
