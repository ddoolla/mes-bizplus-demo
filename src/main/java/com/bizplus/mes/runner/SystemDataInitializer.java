package com.bizplus.mes.runner;

import com.bizplus.mes.domain.code.group.CodeGroupInitializeService;
import com.bizplus.mes.domain.menu.MenuInitializeService;
import com.bizplus.mes.domain.permission.PermissionInitializeService;
import com.bizplus.mes.domain.role.AdminRoleInitializeService;
import com.bizplus.mes.domain.user.AdminUserInitializeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
        prefix = "app",
        name = "system-data.initialize",
        havingValue = "true"
)
@Component
@RequiredArgsConstructor
public class SystemDataInitializer implements ApplicationRunner {

    private final MenuInitializeService menuInitializeService;
    private final CodeGroupInitializeService codeGroupInitializeService;
    private final PermissionInitializeService permissionInitializeService;
    private final AdminRoleInitializeService adminRoleInitializeService;
    private final AdminUserInitializeService adminUserInitializeService;

    @Override
    public void run(ApplicationArguments args) {

        menuInitializeService.initialize();
        codeGroupInitializeService.initialize();
        permissionInitializeService.initialize();
        adminRoleInitializeService.initialize();
        adminUserInitializeService.initialize();
    }
}
