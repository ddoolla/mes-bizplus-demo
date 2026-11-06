package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.result.dto.WorkResultSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WorkResultController {

    private final WorkResultService workResultService;

    @GetMapping("/work-results/modal/select")
    public String viewSelectModal(Model model, WorkResultSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", workResultService.getAll(dto, pageable));

        return "pages/work-result/modal/select :: list";
    }
}
