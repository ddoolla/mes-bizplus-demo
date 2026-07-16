package com.bizplus.mes.domain.permission;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.menu.Menu;
import com.bizplus.mes.domain.menu.MenuCode;
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

        for (PermissionCode pmCode : PermissionCode.values()) {

            MenuCode menuCode = pmCode.getMenu();

            Menu menu = menuRepository.findByCode(menuCode)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND, "menu: " + menuCode.name()));

            Permission existingPm = permissionRepository.findByCode(pmCode).orElse(null);

            if (existingPm != null) {

                existingPm.update(menu, pmCode);

            } else {

                permissionRepository.save(new Permission(menu, pmCode));
            }
        }
    }
}
