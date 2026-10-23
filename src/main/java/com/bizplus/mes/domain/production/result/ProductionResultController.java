package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.production.result.dto.ProductionResultSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/production-results")
@RequiredArgsConstructor
public class ProductionResultController {

    private final ProductionResultService productionResultService;

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCTION_RESULT_READ')")
    @UserAction(menu = MenuCode.PRODUCTION_RESULT, type = ActionType.READ)
    public String viewList(Model model, ProductionResultSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", productionResultService.getAll(dto, pageable));

        return "pages/production-result/list";
    }
}
