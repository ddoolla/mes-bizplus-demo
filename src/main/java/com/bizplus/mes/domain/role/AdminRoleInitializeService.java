package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.permission.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRoleInitializeService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public void initialize() {

        boolean exists = roleRepository.existsByCode("ADMIN");

        Role admin;

        if (!exists) {

            admin = roleRepository.save(new Role("ADMIN", "관리자", null));
        }

        // todo admin - 권한 설정
    }
}
