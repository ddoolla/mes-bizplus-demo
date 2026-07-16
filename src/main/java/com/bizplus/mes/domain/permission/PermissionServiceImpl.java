package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.permission.dto.MenuPermissionDto;
import com.bizplus.mes.domain.permission.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    /**
     * [
     * { menu: ..., permissions: { READ: id, CREATE: id, ... } },
     * { menu: ..., permissions: { READ: id } },
     * ]
     */
    @Transactional
    @Override
    public List<MenuPermissionDto> getPermissions() {

        Map<Long, MenuPermissionDto> grouped = new LinkedHashMap<>();

        permissionRepository.findAll().forEach(permission -> {

            if (permission.getMenu().getParent() == null) {
                return;
            }

            MenuPermissionDto menuPermission = grouped.computeIfAbsent(
                    permission.getMenu().getId(),
                    menuId ->
                            new MenuPermissionDto(
                                    permission.getMenu().getName(),
                                    new HashMap<>()
                            )
            );

            menuPermission.getPermissions().put(
                    permission.getAction(),
                    new PermissionDto(permission.getId(), false)
            );
        });

        return new ArrayList<>(grouped.values());
    }
}
