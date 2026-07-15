package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    RoleListDto getRoles(RoleSearchDto dto, Pageable pageable);

    List<RoleDto> getAllRoles();

    RoleDto getRole(Long id);

    /**
     * 삭제 했더라도 DB에 존재하면 중복으로 간주
     */
    boolean checkCode(Long id, String code);

    Long createRole(RoleCreateDto dto);

    void updateRole(Long id, RoleUpdateDto dto);

    void deleteRoles(List<Long> ids);
}
