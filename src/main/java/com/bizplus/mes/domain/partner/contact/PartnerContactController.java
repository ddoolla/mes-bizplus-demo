package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactCreateDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartnerContactController {

    private final PartnerContactService partnerContactService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping("/partners/{partnerId}/contacts/modal/create")
    public String viewCreateModal(Model model, @PathVariable Long partnerId) {
        model.addAttribute("partnerId", partnerId);
        model.addAttribute("departments", commonCodeService.getCommonCodes(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeService.getCommonCodes(CodeGroupKey.POSITION));

        return "pages/partner-contact/modal/form/create :: form";
    }

    @GetMapping("/partner-contacts/{id}/modal/edit")
    public String viewEditModal(Model model, @PathVariable Long id) {
        model.addAttribute("departments", commonCodeService.getCommonCodes(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeService.getCommonCodes(CodeGroupKey.POSITION));
        model.addAttribute("contact", partnerContactService.getPartnerContact(id));

        return "pages/partner-contact/modal/form/edit :: form";
    }

    @GetMapping("/partner-contacts/{id}")
    @ResponseBody
    public PartnerContactDto readPartnerContact(@PathVariable Long id) {
        return partnerContactService.getPartnerContact(id);
    }

    @PostMapping("/partners/{partnerId}/contacts")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createPartnerContact(@PathVariable Long partnerId, @Valid PartnerContactCreateDto dto) {
        partnerContactService.createPartnerContact(partnerId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/partner-contacts/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> updatePartnerContact(@PathVariable Long id, @Valid PartnerContactUpdateDto dto) {
        partnerContactService.updatePartnerContact(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping("/partner-contacts")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deletePartnerContact(@RequestBody List<Long> ids) {
        partnerContactService.deletePartnerContacts(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
