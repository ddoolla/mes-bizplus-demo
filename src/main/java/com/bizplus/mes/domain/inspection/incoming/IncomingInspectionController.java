package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSearchDto;
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
@RequestMapping("/incoming-inspections")
@RequiredArgsConstructor
public class IncomingInspectionController {

    private final IncomingInspectionService incomingInspectionService;

    @GetMapping
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_READ')")
    @UserAction(menu = MenuCode.INCOMING_INSPECTION, type = ActionType.READ)
    public String viewList(Model model, IncomingInspectionSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", incomingInspectionService.getAll(dto, pageable));

        return "pages/incoming-inspection/list";
    }
}
