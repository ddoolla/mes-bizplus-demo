package com.bizplus.mes.domain.log.auth;

import com.bizplus.mes.domain.log.auth.dto.UserAuthLogSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserAuthLogController {

    private final UserAuthLogService userAuthLogService;

    @GetMapping("/user-logs/auth")
    public String viewList(Model model,
                           UserAuthLogSearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("data", userAuthLogService.getUserAuthLogs(dto, pageable));

        return "pages/log/auth-list";
    }
}
