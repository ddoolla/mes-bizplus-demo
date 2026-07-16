package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/code-groups")
@RequiredArgsConstructor
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @GetMapping
    public String viewList(Model model,
                           @RequestParam(required = false) String menuName,
                           @RequestParam(required = false) String name) {

        List<CodeGroupDto> codeGroups = codeGroupService.getCodeGroups(menuName, name);

        model.addAttribute("codeGroups", codeGroups);

        return "pages/code-group/list";
    }
}
