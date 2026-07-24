package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.partner.dto.PartnerCreateDto;
import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import com.bizplus.mes.domain.partner.dto.PartnerUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final MessageService messageService;

    @GetMapping
    public String viewList(Model model,
                           PartnerSearchDto dto,
                           @PageableDefault Pageable pageable) {

        model.addAttribute("partnerTypes", PartnerType.values());
        model.addAttribute("data", partnerService.getPartners(dto, pageable));
        model.addAttribute("selectedType", dto.getType());
        model.addAttribute("selectedActive", dto.getActive());

        return "/pages/partner/list";
    }

    @GetMapping("/{id}")
    public String viewDetail(Model model, @PathVariable Long id) {

        model.addAttribute("partner", partnerService.getPartner(id));

        return "/pages/partner/detail";
    }

    @GetMapping("/new")
    public String viewNew(Model model) {

        model.addAttribute("partnerTypes", PartnerType.values());

        return "/pages/partner/new";
    }

    @GetMapping("/{id}/edit")
    public String viewEdit(Model model, @PathVariable Long id) {

        model.addAttribute("partnerTypes", PartnerType.values());
        model.addAttribute("partner", partnerService.getPartner(id));

        return "/pages/partner/edit";
    }

    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkPartnerCode(@RequestParam(required = false) Long id,
                                    @RequestParam String code) {

        return partnerService.checkPartnerCode(id, code);
    }

    @PostMapping
    public String createPartner(@Valid PartnerCreateDto dto, RedirectAttributes reAtt) {

        partnerService.createPartner(dto);

        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/partners";
    }

    @PostMapping("/{id}")
    public String updatePartner(@PathVariable Long id,
                                @Valid PartnerUpdateDto dto,
                                RedirectAttributes reAtt) {

        partnerService.updatePartner(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get("common.updated"));

        return "redirect:/partners/{id}";
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deletePartners(@RequestBody List<Long> ids) {

        partnerService.deletePartners(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
