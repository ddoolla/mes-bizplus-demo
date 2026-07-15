package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.role.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleReader roleReader;

    @Override
    public RoleListDto getRoles(RoleSearchDto dto, Pageable pageable) {

        Page<RoleDto> rolePage = roleRepository.findRoles(dto, pageable);

        return new RoleListDto(
                rolePage.getContent(),
                Pagination.of(rolePage));
    }

    @Override
    public List<RoleDto> getAllRoles() {

        return roleRepository.findAllByDeletedAtIsNull().stream()
                .map(RoleMapper::toDto).toList();
    }

    @Override
    public RoleDto getRole(Long id) {

        return roleRepository.findRole(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROLE_NOT_FOUND, id));
    }

    @Override
    public boolean checkCode(Long id, String code) {

        boolean exists = roleRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public Long createRole(RoleCreateDto dto) {

        Role newRole = RoleMapper.toEntity(dto);

        return roleRepository.save(newRole).getId();
    }

    @Transactional
    @Override
    public void updateRole(Long id, RoleUpdateDto dto) {

        roleReader.getById(id)
                .update(dto.getName(), dto.getDescription());
    }

    @Transactional
    @Override
    public void deleteRoles(List<Long> ids) {

        ids.forEach(id -> {
            roleReader.getById(id).delete();
        });
    }
}
