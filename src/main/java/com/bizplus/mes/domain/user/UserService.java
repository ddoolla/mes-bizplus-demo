package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.user.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserListDto getUsers(UserSearchDto dto, Pageable pageable);

    UserDto getUser(Long id);

    boolean checkUserId(String userId);

    void createUser(UserCreateDto dto);

    void updateUser(Long id, UserUpdateDto dto);

    void deleteUsers(List<Long> ids);
}
