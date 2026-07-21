package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.permission.PermissionAction;
import com.bizplus.mes.domain.permission.PermissionService;
import com.bizplus.mes.domain.permission.dto.MenuPermissionDto;
import com.bizplus.mes.domain.role.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public String viewList(Model model,
                           RoleSearchDto dto,
                           @PageableDefault Pageable pageable) {

        RoleListDto roles = roleService.getRoles(dto, pageable);

        model.addAttribute("data", roles);

        return "pages/role/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {

        RolePermissionDto roleDetail = roleService.getRole(id);

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("roleDetail", roleDetail);

        return "pages/role/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public String viewNew(Model model) {

        List<MenuPermissionDto> menuPermissions = permissionService.getPermissions();

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("menuPermissions", menuPermissions);

        return "pages/role/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {

        RolePermissionDto roleDetail = roleService.getRole(id);

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("roleDetail", roleDetail);

        return "pages/role/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkCode(Long id, String code) {

        return roleService.checkCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public String createRole(@Valid RoleCreateDto dto, RedirectAttributes reAtt) {

        roleService.createRole(dto);

        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/roles";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public String updateRole(@PathVariable Long id,
                             @Valid RoleUpdateDto dto,
                             RedirectAttributes reAtt) {

        roleService.updateRole(id, dto);

        reAtt.addFlashAttribute("message", messageService.get("common.updated"));

        return "redirect:/roles/" + id;
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deleteRoles(@RequestBody List<Long> ids) {

        roleService.deleteRoles(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
