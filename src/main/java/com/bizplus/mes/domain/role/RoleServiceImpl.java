package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.permission.Permission;
import com.bizplus.mes.domain.permission.PermissionRepository;
import com.bizplus.mes.domain.permission.PermissionService;
import com.bizplus.mes.domain.permission.dto.MenuPermissionDto;
import com.bizplus.mes.domain.role.dto.*;
import com.bizplus.mes.domain.role.permission.RolePermission;
import com.bizplus.mes.domain.role.permission.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    private final PermissionService permissionService;

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
    public RoleDetailDto getRole(Long id) {

        Role role = roleReader.getById(id);

        Set<Long> permissionIds = rolePermissionRepository.findAllByRole(role).stream()
                .map(rolePermission -> rolePermission.getPermission().getId())
                .collect(Collectors.toSet());

        List<MenuPermissionDto> menuPermissions = permissionService.getPermissions();

        menuPermissions.forEach(mp -> {
            mp.getPermissions().values().forEach(permission -> {
                permission.setChecked(
                        permissionIds.contains(permission.getId())
                );
            });
        });

        return new RoleDetailDto(
                RoleMapper.toDto(role),
                menuPermissions);
    }

    @Override
    public boolean checkCode(Long id, String code) {

        boolean exists = roleRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Transactional
    @Override
    public void createRole(RoleCreateDto dto) {

        Role newRole = roleRepository.save(RoleMapper.toEntity(dto));

        List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());

        permissions.forEach(permission -> {
            rolePermissionRepository.save(new RolePermission(newRole, permission));
        });
    }

    @Transactional
    @Override
    public void updateRole(Long id, RoleUpdateDto dto) {

        Role role = roleReader.getById(id);

        role.update(dto.getName(), dto.getDescription());

        rolePermissionRepository.deleteAllByRole(role);

        List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());

        if (permissions.size() != dto.getPermissionIds().size()) {
            throw new BusinessException(ErrorCode.INVALID_PERMISSION);
        }

        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> new RolePermission(role, permission))
                .toList();

        rolePermissionRepository.saveAll(rolePermissions);
    }

    @Transactional
    @Override
    public void deleteRoles(List<Long> ids) {

        // todo role - 논리 삭제, user-role - 물리 삭제
        // todo 사용자에게 부여 중이면 삭제 제한
        // todo 또는 삭제하고 기본 권한 부여하는 방식 ? (기본 권한 - 모든 메뉴 읽기 권한)

        ids.forEach(id -> {
            roleReader.getById(id).delete();
        });
    }
}
