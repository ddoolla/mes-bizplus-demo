package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.lot.dto.LotSearchDto;
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
@RequestMapping("/lots")
@RequiredArgsConstructor
public class LotController {

    private final LotService lotService;

    @GetMapping
    @PreAuthorize("hasAuthority('LOT_READ')")
    @UserAction(menu = MenuCode.LOT, type = ActionType.READ)
    public String viewList(Model model, LotSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", lotService.getAll(dto, pageable));

        return "pages/lot/list";
    }
}
