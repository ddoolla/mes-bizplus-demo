package com.bizplus.mes.runner;

import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.user-id}")
    private String userId;

    @Value("${app.admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.existsByUserId(userId)) {
            return;
        }

        User admin = new User(
                userId,
                passwordEncoder.encode(password),
                "관리자",
                null,
                null,
                null,
                null
        );

        userRepository.save(admin);
    }
}
