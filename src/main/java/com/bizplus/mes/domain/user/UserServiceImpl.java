package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.role.RoleReader;
import com.bizplus.mes.domain.user.dto.*;
import com.bizplus.mes.domain.user.role.UserRole;
import com.bizplus.mes.domain.user.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final CommonCodeReader commonCodeReader;
    private final UserReader userReader;
    private final RoleReader roleReader;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserListDto getUsers(UserSearchDto dto, Pageable pageable) {

        Page<UserDto> userPage = userRepository.findUsers(dto, pageable);

        return new UserListDto(
                userPage.getContent(),
                Pagination.of(userPage));
    }

    @Override
    public UserDto getUser(Long id) {

        return userRepository.findUser(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, id));
    }

    @Override
    public boolean checkUserId(String userId) {

        boolean exists = userRepository.existsByUserId(userId);

        return !exists;
    }

    @Transactional
    @Override
    public Long createUser(UserCreateDto dto) {

        CommonCode departmentCode = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode positionCode = commonCodeReader.getOrNull(dto.getPositionId());

        User newUser = userRepository.save(UserMapper
                .toEntity(
                        dto,
                        passwordEncoder.encode(dto.getPassword()),
                        departmentCode,
                        positionCode
                ));

        Role role = roleReader.getById(dto.getRoleId());

        userRoleRepository.save(new UserRole(newUser, role));

        return newUser.getId();
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserUpdateDto dto) {

        CommonCode departmentCode = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode positionCode = commonCodeReader.getOrNull(dto.getPositionId());

        User user = userReader.getById(id);
        Role role = roleReader.getById(dto.getRoleId());

        UserRole userRole = userRoleRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, user.getUserId()));

        user.update(departmentCode,
                positionCode,
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getRemark());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {

            user.updatePassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (!userRole.getRole().getId().equals(role.getId())) {
            // 사용자별 1개의 역할만 부여
            userRoleRepository.deleteByUser(user);
            userRoleRepository.save(new UserRole(user, role));
        }
    }

    @Transactional
    @Override
    public void deleteUsers(List<Long> ids) {

        ids.forEach(id -> {
            userReader.getById(id).delete();
        });
    }
}
