package com.bizplus.mes.runner;

import com.bizplus.mes.domain.menu.MenuInitializeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuInitializer implements ApplicationRunner {

    private final MenuInitializeService menuInitializeService;

    @Override
    public void run(ApplicationArguments args) {
        menuInitializeService.initialize();
    }
}
