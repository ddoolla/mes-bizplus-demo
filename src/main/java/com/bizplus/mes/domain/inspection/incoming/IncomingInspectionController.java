package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionCreateDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSearchDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionUpdateDto;
import com.bizplus.mes.domain.inspection.incoming.facade.IncomingInspectionCreateService;
import com.bizplus.mes.domain.inspection.incoming.facade.IncomingInspectionUpdateService;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/incoming-inspections")
@RequiredArgsConstructor
public class IncomingInspectionController {

    private final MessageService messageService;
    private final IncomingInspectionService incomingInspectionService;
    private final InspectionResultService inspectionResultService;

    private final IncomingInspectionCreateService incomingInspectionCreateService;
    private final IncomingInspectionUpdateService incomingInspectionUpdateService;

    @GetMapping
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_READ')")
    @UserAction(menu = MenuCode.INCOMING_INSPECTION, type = ActionType.READ)
    public String viewList(Model model, IncomingInspectionSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", incomingInspectionService.getAll(dto, pageable));

        return "pages/incoming-inspection/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        IncomingInspectionDto incomingInspection = incomingInspectionService.getById(id);

        model.addAttribute("incomingInspection", incomingInspection);
        model.addAttribute("inspectionResults", inspectionResultService.getAll(incomingInspection.getInspection().getId()));

        return "pages/incoming-inspection/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_CREATE')")
    public String viewNew(Model model) {
        return "pages/incoming-inspection/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        IncomingInspectionDto incomingInspection = incomingInspectionService.getById(id);

        model.addAttribute("incomingInspection", incomingInspection);
        model.addAttribute("inspectionResults", inspectionResultService.getAll(incomingInspection.getInspection().getId()));

        return "pages/incoming-inspection/edit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_CREATE')")
    @UserAction(menu = MenuCode.INCOMING_INSPECTION, type = ActionType.CREATE)
    public String create(IncomingInspectionCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = incomingInspectionCreateService.create(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/incoming-inspections/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('INCOMING_INSPECTION_UPDATE')")
    @UserAction(menu = MenuCode.INCOMING_INSPECTION, type = ActionType.UPDATE)
    public String update(@PathVariable Long id, IncomingInspectionUpdateDto dto, RedirectAttributes reAtt) {
        incomingInspectionUpdateService.update(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/incoming-inspections/{id}";
    }
}
