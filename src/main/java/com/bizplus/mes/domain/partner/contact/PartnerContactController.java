package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
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
    private final MessageService messageService;

    private final CommonCodeReader commonCodeReader;

    @GetMapping("/partners/{partnerId}/contacts/new")
    public String viewNew(Model model, @PathVariable Long partnerId) {

        model.addAttribute("partnerId", partnerId);
        model.addAttribute("departments", commonCodeReader.getByGroup(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeReader.getByGroup(CodeGroupKey.POSITION));

        return "pages/partner-contact/fragments/modal/create-content :: content";
    }

    @GetMapping("/partner-contacts/{id}/edit")
    public String viewEdit(Model model, @PathVariable Long id) {

        model.addAttribute("departments", commonCodeReader.getByGroup(CodeGroupKey.DEPARTMENT));
        model.addAttribute("positions", commonCodeReader.getByGroup(CodeGroupKey.POSITION));
        model.addAttribute("contact", partnerContactService.getPartnerContact(id));

        return "pages/partner-contact/fragments/modal/edit-content :: content";
    }

    @GetMapping("/partner-contacts/{id}")
    @ResponseBody
    public PartnerContactDto readPartnerContact(@PathVariable Long id) {

        return partnerContactService.getPartnerContact(id);
    }

    @PostMapping("/partners/{partnerId}/contacts")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createPartnerContact(@PathVariable Long partnerId,
                                                                  @Valid PartnerContactCreateDto dto) {
        partnerContactService.createPartnerContact(partnerId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/partner-contacts/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> updatePartnerContact(@PathVariable Long id,
                                                                  @Valid PartnerContactUpdateDto dto) {
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
