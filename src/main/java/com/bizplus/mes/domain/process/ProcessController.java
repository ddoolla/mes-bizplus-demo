package com.bizplus.mes.domain.process;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.process.dto.ProcessCreateDto;
import com.bizplus.mes.domain.process.dto.ProcessSearchDto;
import com.bizplus.mes.domain.process.dto.ProcessUpdateDto;
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
@RequestMapping("/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('PROCESS_READ')")
    @UserAction(menu = MenuCode.PROCESS, type = ActionType.READ)
    public String viewList(Model model,
                           ProcessSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("data", processService.getProcesses(dto, pageable));

        return "pages/process/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROCESS_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("process", processService.getProcess(id));

        return "pages/process/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('PROCESS_READ')")
    public String viewNew() {
        return "pages/process/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('PROCESS_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("process", processService.getProcess(id));

        return "pages/process/edit";
    }

    @GetMapping("/modal/select/multi")
    public String viewMultiSelectModal(Model model,
                                      ProcessSearchDto dto,
                                      @PageableDefault Pageable pageable) {
        model.addAttribute("data", processService.getProcesses(dto, pageable));

        return "pages/process/modal/select/multi :: list";
    }

    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkCode(@RequestParam(required = false) Long id,
                             @RequestParam String code) {
        return processService.checkProcessCode(id, code);
    }

    @PostMapping
    public String createProcess(@Valid ProcessCreateDto dto, RedirectAttributes reAtt) {
        processService.createProcess(dto);

        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/processes";
    }

    @PostMapping("/{id}")
    public String updateProcess(@PathVariable Long id,
                                @Valid ProcessUpdateDto dto,
                                RedirectAttributes reAtt) {
        processService.updateProcess(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/processes/{id}";
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteProcesses(@RequestBody List<Long> ids) {
        processService.deleteProcesses(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
