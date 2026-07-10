package com.bizplus.mes.domain.permission;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import com.bizplus.mes.domain.menu.Menu;
import com.bizplus.mes.domain.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionInitializeService {

    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public void initialize() {

        for (PermissionSeed seed : PermissionSeed.values()) {

            String menuCode = seed.getMenu().getCode();
            String code = seed.name();
            String name = seed.getName();
            PermissionAction action = seed.getAction();

            Menu menu = menuRepository.findByCode(menuCode)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.MENU_NOT_FOUND, menuCode));

            Permission permission = permissionRepository.findByCode(code).orElse(null);

            if (permission != null) {

                permission.update(menu, code, name, action);

            } else {

                Permission newPermission = new Permission(menu, code, name, action);

                permissionRepository.save(newPermission);
            }
        }
    }
}
