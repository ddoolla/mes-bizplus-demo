package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/in-process-inspections")
@RequiredArgsConstructor
public class InProcessInspectionController {

    private final InProcessInspectionService inProcessInspectionService;

    @GetMapping
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_READ')")
    @UserAction(menu = MenuCode.IN_PROCESS_INSPECTION, type = ActionType.READ)
    public String viewList(Model model, InProcessInspectionSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", inProcessInspectionService.getAll(dto, pageable));

        return "pages/in-process-inspection/list";
    }
}
