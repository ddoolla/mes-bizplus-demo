package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User getById(Long id) {

        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, id));
    }
}
