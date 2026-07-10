package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    RoleListDto getRoles(RoleSearchDto dto, Pageable pageable);

    RoleDto getRole(Long id);

    Long createRole(RoleCreateDto dto);

    void updateRole(Long id, RoleUpdateDto dto);

    void deleteRoles(List<Long> ids);
}
