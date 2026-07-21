package com.bizplus.mes.domain.user;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.role.RoleService;
import com.bizplus.mes.domain.role.dto.RoleDto;
import com.bizplus.mes.domain.user.dto.*;
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

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public String viewList(Model model,
                           UserSearchDto dto,
                           @PageableDefault Pageable pageable) {

        UserListDto users = userService.getUsers(dto, pageable);

        model.addAttribute("data", users);

        return "pages/user/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {

        UserDto user = userService.getUser(id);

        model.addAttribute("user", user);

        return "pages/user/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public String viewNew(Model model) {

        List<RoleDto> roles = roleService.getAllRoles();
        // todo 공통코드 불러오기 추가

        model.addAttribute("roles", roles);

        return "pages/user/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {

        UserDto user = userService.getUser(id);
        List<RoleDto> roles = roleService.getAllRoles();
        // todo 공통코드 불러오기 추가

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "pages/user/edit";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkId(String userId) {

        return userService.checkUserId(userId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public String createUser(@Valid UserCreateDto dto, RedirectAttributes reAtt) {

        userService.createUser(dto);

        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/users";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
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
    public ResponseEntity<ApiResponse<Void>> deleteUsers(@RequestBody List<Long> ids) {

        userService.deleteUsers(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
