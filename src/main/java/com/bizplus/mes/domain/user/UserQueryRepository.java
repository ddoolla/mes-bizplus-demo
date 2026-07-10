package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.user.dto.UserDto;
import com.bizplus.mes.domain.user.dto.UserSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserQueryRepository {

    Page<UserDto> findUsers(UserSearchDto dto, Pageable pageable);

    Optional<UserDto> findUser(Long id);
}
