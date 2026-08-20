package com.bizplus.mes.domain.routing;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.ItemGroup;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.routing.dto.RoutingCreateDto;
import com.bizplus.mes.domain.routing.dto.RoutingSearchDto;
import com.bizplus.mes.domain.routing.dto.RoutingUpdateDto;
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
@RequestMapping("/routings")
@RequiredArgsConstructor
public class RoutingController {

    private final CommonCodeService commonCodeService;
    private final RoutingService routingService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROUTING_READ')")
    @UserAction(menu = MenuCode.ROUTING, type = ActionType.READ)
    public String viewList(Model model,
                           RoutingSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("data", routingService.getRoutings(dto, pageable));

        return "pages/routing/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROUTING_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("routing", routingService.getRouting(id));

        return "pages/routing/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ROUTING_READ')")
    public String viewNew(Model model) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemGroup.PRODUCT.getTypes());

        return "pages/routing/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ROUTING_READ')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("routing", routingService.getRouting(id));

        return "pages/routing/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROUTING_READ')")
    public boolean checkRoutingCode(@RequestParam(required = false) Long id,
                                    @RequestParam String code) {
        return routingService.checkRoutingCode(id, code);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROUTING_CREATE')")
    @UserAction(menu = MenuCode.ROUTING, type = ActionType.CREATE)
    public String createRouting(@Valid RoutingCreateDto dto, RedirectAttributes reAtt) {
        Long createdId = routingService.createRouting(dto);

        reAtt.addAttribute("id", createdId);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/routings/{id}";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ROUTING_UPDATE')")
    @UserAction(menu = MenuCode.ROUTING, type = ActionType.UPDATE)
    public String updateRouting(@PathVariable Long id,
                                @Valid RoutingUpdateDto dto,
                                RedirectAttributes reAtt) {
        routingService.updateRouting(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/routings/{id}";
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('ROUTING_DELETE')")
    @UserAction(menu = MenuCode.ROUTING, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteRoutings(@RequestBody List<Long> ids) {
        routingService.deleteRoutings(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
