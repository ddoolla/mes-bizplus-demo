package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.lot.dto.LotSearchDto;
import com.bizplus.mes.domain.lot.dto.LotUpdateDto;
import com.bizplus.mes.domain.menu.MenuCode;
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
@RequestMapping("/lots")
@RequiredArgsConstructor
public class LotController {

    private final MessageService messageService;
    private final LotService lotService;

    @GetMapping
    @PreAuthorize("hasAuthority('LOT_READ')")
    @UserAction(menu = MenuCode.LOT, type = ActionType.READ)
    public String viewList(Model model, LotSearchDto dto, @PageableDefault Pageable pageable) {
        model.addAttribute("data", lotService.getAll(dto, pageable));

        return "pages/lot/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LOT_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("lot", lotService.getById(id));

        return "pages/lot/detail";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('LOT_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("lot", lotService.getById(id));

        return "pages/lot/edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('LOT_UPDATE')")
    @UserAction(menu = MenuCode.LOT, type = ActionType.UPDATE)
    public String update(@PathVariable Long id, @Valid LotUpdateDto dto, RedirectAttributes reAtt) {
        lotService.update(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/lots/{id}";
    }
}
