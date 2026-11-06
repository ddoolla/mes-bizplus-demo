package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionCreateDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.inspection.inprocess.facade.InProcessInspectionCreateService;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.uom.conversion.UomConversionReader;
import jakarta.validation.Valid;
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
@RequestMapping("/in-process-inspections")
@RequiredArgsConstructor
public class InProcessInspectionController {

    private final MessageService messageService;
    private final InProcessInspectionService inProcessInspectionService;
    private final InspectionResultService inspectionResultService;

    private final InProcessInspectionCreateService inProcessInspectionCreateService;

    private final UomConversionReader uomConversionReader;

    @GetMapping
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_READ')")
    @UserAction(menu = MenuCode.IN_PROCESS_INSPECTION, type = ActionType.READ)
    public String viewList(Model model, InProcessInspectionSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", inProcessInspectionService.getAll(dto, pageable));

        return "pages/in-process-inspection/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        InProcessInspectionDto inProcessInspection = inProcessInspectionService.getById(id);

        model.addAttribute("inProcessInspection", inProcessInspection);
        model.addAttribute("inspectionResults", inspectionResultService.getAll(inProcessInspection.getInspection().getId()));

        return "pages/in-process-inspection/detail";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        InProcessInspectionDto inProcessInspection = inProcessInspectionService.getById(id);

        model.addAttribute("inProcessInspection", inProcessInspection);
        model.addAttribute("inspectionResults", inspectionResultService.getAll(inProcessInspection.getInspection().getId()));
        model.addAttribute("convertibleUoms", uomConversionReader.getConvertibleUoms(inProcessInspection.getWorkResult().getItem().uom().id()));

        return "pages/in-process-inspection/edit";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_CREATE')")
    public String viewNew() {
        return "pages/in-process-inspection/new";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('IN_PROCESS_INSPECTION_CREATE')")
    @UserAction(menu = MenuCode.IN_PROCESS_INSPECTION, type = ActionType.CREATE)
    public String create(@Valid InProcessInspectionCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = inProcessInspectionCreateService.create(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/in-process-inspections/{id}/edit";
    }
}
