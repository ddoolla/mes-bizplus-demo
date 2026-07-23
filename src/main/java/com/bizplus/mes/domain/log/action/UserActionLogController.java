package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.domain.log.action.dto.UserActionLogSearchDto;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserActionLogController {

    private final UserActionLogService userActionLogService;

    @GetMapping("/user-logs/action")
    public String viewList(Model model,
                           UserActionLogSearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("menuCodes", MenuCode.values());
        model.addAttribute("actionTypes", ActionType.values());
        model.addAttribute("data", userActionLogService.getUserActionLogs(dto, pageable));
        model.addAttribute("selectedType", dto.getType());
        model.addAttribute("selectedMenuCode", dto.getMenuCode());

        return "pages/log/action-list";
    }
}
