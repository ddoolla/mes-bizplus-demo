package com.bizplus.mes.security;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.role.permission.RolePermissionRepository;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserRepository;
import com.bizplus.mes.domain.user.role.UserRole;
import com.bizplus.mes.domain.user.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserIdAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found [ID: " + username + "]"));

        UserRole userRole = userRoleRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ROLE_NOT_FOUND, "userId: " + user.getUserId()));

        Set<GrantedAuthority> authorities = rolePermissionRepository.findAllByRole(userRole.getRole()).stream()
                .map(rolePermission -> new SimpleGrantedAuthority(
                        rolePermission.getPermission().getCode().name()
                ))
                .collect(Collectors.toSet());

        return new CustomUserDetails(user, authorities);
    }
}
