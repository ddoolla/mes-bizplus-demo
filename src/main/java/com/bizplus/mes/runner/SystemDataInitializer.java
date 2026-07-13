package com.bizplus.mes.runner;

import com.bizplus.mes.domain.menu.MenuInitializeService;
import com.bizplus.mes.domain.permission.PermissionInitializeService;
import com.bizplus.mes.domain.role.AdminRoleInitializeService;
import com.bizplus.mes.domain.user.AdminUserInitializeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemDataInitializer implements ApplicationRunner {

    private final MenuInitializeService menuInitializeService;
    private final PermissionInitializeService permissionInitializeService;
    private final AdminRoleInitializeService adminRoleInitializeService;
    private final AdminUserInitializeService adminUserInitializeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        menuInitializeService.initialize();
        permissionInitializeService.initialize();
        adminRoleInitializeService.initialize();
        adminUserInitializeService.initialize();
    }
}
