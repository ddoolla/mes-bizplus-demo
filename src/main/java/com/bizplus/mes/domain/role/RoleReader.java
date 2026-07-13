package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleReader {

    private final RoleRepository roleRepository;

    public Role getById(Long id) {

        return roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROLE_NOT_FOUND, id));
    }

    public Role getByCode(String code) {

        return roleRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROLE_NOT_FOUND, code));
    }
}
