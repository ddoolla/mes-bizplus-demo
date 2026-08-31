package com.bizplus.mes.domain.worker;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.worker.dto.WorkerCreateDto;
import com.bizplus.mes.domain.worker.dto.WorkerSearchDto;
import com.bizplus.mes.domain.worker.dto.WorkerUpdateDto;
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
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('WORKER_READ')")
    @UserAction(menu = MenuCode.WORKER, type = ActionType.READ)
    public String viewList(Model model, WorkerSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("departments", commonCodeService.getCommonCodes(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeService.getCommonCodes(CodeGroupKey.POSITION));
        model.addAttribute("data", workerService.getWorkers(dto, pageable));

        return "pages/worker/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WORKER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("worker", workerService.getWorker(id));

        return "pages/worker/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('WORKER_READ')")
    public String viewNew(Model model) {
        model.addAttribute("departments", commonCodeService.getCommonCodes(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeService.getCommonCodes(CodeGroupKey.POSITION));

        return "pages/worker/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('WORKER_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("worker", workerService.getWorker(id));

        return "pages/worker/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    @PreAuthorize("hasAuthority('WORKER_READ')")
    public boolean checkWorkerCode(@RequestParam(required = false) Long id,
                                   @RequestParam String code) {
        return workerService.checkWorkerCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WORKER_CREATE')")
    @UserAction(menu = MenuCode.WORKER, type = ActionType.CREATE)
    public String createWorker(@Valid WorkerCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = workerService.createWorker(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/workers/{id}/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('WORKER_UPDATE')")
    @UserAction(menu = MenuCode.WORKER, type = ActionType.UPDATE)
    public String updateWorker(@PathVariable Long id,
                               @Valid WorkerUpdateDto dto,
                               RedirectAttributes reAtt) {
        workerService.updateWorker(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/workers/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('WORKER_DELETE')")
    @UserAction(menu = MenuCode.WORKER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteWorkers(@RequestBody List<Long> ids) {
        workerService.deleteWorkers(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
