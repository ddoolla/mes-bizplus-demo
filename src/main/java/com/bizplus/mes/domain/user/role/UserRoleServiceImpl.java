package com.bizplus.mes.domain.user.role;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.role.RoleReader;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    private final UserReader userReader;
    private final RoleReader roleReader;

    @Override
    public void createUserRole(Long userId, Long roleId) {
        User user = userReader.getById(userId);
        Role role = roleReader.getById(roleId);

        userRoleRepository.save(new UserRole(user, role));
    }

    @Transactional
    @Override
    public void updateUserRole(Long userId, Long roleId) {
        User user = userReader.getById(userId);
        Role role = roleReader.getById(roleId);

        UserRole userRole = userRoleRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.USER_ROLE_NOT_FOUND, "userId: " + user.getUserId()));

        if (!userRole.getRole().getId().equals(role.getId())) {
            // 사용자별 1개의 역할만 부여
            userRoleRepository.deleteByUser(user);
            userRoleRepository.save(new UserRole(user, role));
        }
    }
}
