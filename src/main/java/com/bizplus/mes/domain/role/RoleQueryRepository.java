package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.RoleDto;
import com.bizplus.mes.domain.role.dto.RoleSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleQueryRepository {

    Page<RoleDto> findRoles(RoleSearchDto dto, Pageable pageable);

    Optional<RoleDto> findRole(Long id);
}
