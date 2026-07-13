package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.user.dto.UserListDto;
import com.bizplus.mes.domain.user.dto.UserSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String viewListPage(Model model,
                               UserSearchDto dto,
                               @PageableDefault Pageable pageable) {

        UserListDto users = userService.getUsers(dto, pageable);

        model.addAttribute("data", users);

        return "pages/user/list";
    }
}
