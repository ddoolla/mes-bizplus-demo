package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.role.RoleService;
import com.bizplus.mes.domain.user.dto.UserCreateDto;
import com.bizplus.mes.domain.user.dto.UserSearchDto;
import com.bizplus.mes.domain.user.dto.UserUpdateDto;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final MessageService messageService;

    private final CommonCodeReader commonCodeReader;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    @UserAction(menu = MenuCode.USER, type = ActionType.READ)
    public String viewList(Model model,
                           UserSearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("data", userService.getUsers(dto, pageable));

        return "pages/user/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.getUser(id));

        return "pages/user/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public String viewNew(Model model) {

        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("positions", commonCodeReader.getByGroup(CodeGroupKey.POSITION));
        model.addAttribute("departments", commonCodeReader.getByGroup(CodeGroupKey.DEPARTMENT));

        return "pages/user/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("positions", commonCodeReader.getByGroup(CodeGroupKey.POSITION));
        model.addAttribute("departments", commonCodeReader.getByGroup(CodeGroupKey.DEPARTMENT));

        return "pages/user/edit";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkId(String userId) {

        return userService.checkUserId(userId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @UserAction(menu = MenuCode.USER, type = ActionType.CREATE)
    public String createUser(@Valid UserCreateDto dto, RedirectAttributes reAtt) {

        userService.createUser(dto);

        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/users";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @UserAction(menu = MenuCode.USER, type = ActionType.UPDATE)
    public String updateUser(@PathVariable Long id,
                             @Valid UserUpdateDto dto,
                             RedirectAttributes reAtt) {

        userService.updateUser(id, dto);

        reAtt.addFlashAttribute("message", messageService.get("common.updated"));

        return "redirect:/users/" + id;
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @UserAction(menu = MenuCode.USER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteUsers(@RequestBody List<Long> ids) {

        userService.deleteUsers(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
