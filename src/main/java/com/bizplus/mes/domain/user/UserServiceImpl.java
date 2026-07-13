package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.user.dto.*;
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

    private final UserReader userReader;
    private final CommonCodeReader commonCodeReader;

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
    public Long createUser(UserCreateDto dto) {

        CommonCode departmentCode = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode positionCode = commonCodeReader.getOrNull(dto.getPositionId());

        User newUser = UserMapper.toEntity(
                dto,
                passwordEncoder.encode(dto.getPassword()),
                departmentCode,
                positionCode
        );

        // todo 권한 관련 로직

        return userRepository.save(newUser).getId();
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserUpdateDto dto) {

        CommonCode departmentCode = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode positionCode = commonCodeReader.getOrNull(dto.getPositionId());

        userReader.getById(id)
                .update(dto.getName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        departmentCode,
                        positionCode);

        // todo 권한 관련 로직
    }

    @Transactional
    @Override
    public void deleteUsers(List<Long> ids) {

        ids.forEach(id -> {
            userReader.getById(id).delete();
        });
    }
}
