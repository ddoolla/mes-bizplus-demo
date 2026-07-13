package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.role.RoleReader;
import com.bizplus.mes.domain.user.role.UserRole;
import com.bizplus.mes.domain.user.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserInitializeService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final RoleReader roleReader;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.user-id}")
    private String userId;

    @Value("${app.admin.password}")
    private String password;

    @Value("${app.admin.role.code}")
    private String roleCode;

    public void initialize() {

        User admin = userRepository.findByUserIdAndDeletedAtIsNull(userId).orElse(null);

        if (admin == null) {

            admin = userRepository.save(new User(
                    null,
                    null,
                    userId,
                    passwordEncoder.encode(password),
                    "관리자",
                    null,
                    null,
                    null
            ));
        }

        Role adminRole = roleReader.getByCode(roleCode);

        if (!userRoleRepository.existsByUserAndRole(admin, adminRole)) {

            userRoleRepository.save(new UserRole(admin, adminRole));
        }
    }
}
