package com.bizplus.mes.domain.role;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.permission.PermissionAction;
import com.bizplus.mes.domain.permission.PermissionService;
import com.bizplus.mes.domain.role.dto.RoleCreateDto;
import com.bizplus.mes.domain.role.dto.RoleSearchDto;
import com.bizplus.mes.domain.role.dto.RoleUpdateDto;
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
    @UserAction(menu = MenuCode.ROLE, type = ActionType.READ)
    public String viewList(Model model,
                           RoleSearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("data", roleService.getRoles(dto, pageable));

        return "pages/role/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("roleDetail", roleService.getRole(id));

        return "pages/role/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public String viewNew(Model model) {

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("menuPermissions", permissionService.getPermissions());

        return "pages/role/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {

        model.addAttribute("actions", PermissionAction.values());
        model.addAttribute("roleDetail", roleService.getRole(id));

        return "pages/role/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkCode(Long id, String code) {

        return roleService.checkCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @UserAction(menu = MenuCode.ROLE, type = ActionType.CREATE)
    public String createRole(@Valid RoleCreateDto dto, RedirectAttributes reAtt) {

        roleService.createRole(dto);

        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/roles";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @UserAction(menu = MenuCode.ROLE, type = ActionType.UPDATE)
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
    @UserAction(menu = MenuCode.ROLE, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteRoles(@RequestBody List<Long> ids) {

        roleService.deleteRoles(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
