package com.bizplus.mes.domain.code.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/code-groups")
@RequiredArgsConstructor
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @GetMapping
    public String viewList(Model model,
                           @RequestParam(required = false) String menuName,
                           @RequestParam(required = false) String name) {

        model.addAttribute("codeGroups", codeGroupService.getCodeGroups(menuName, name));

        return "pages/code-group/list";
    }
}
