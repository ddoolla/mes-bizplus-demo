package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactCreateDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartnerContactController {

    private final PartnerContactService partnerContactService;
    private final MessageService messageService;

    @PostMapping("/partners/{partnerId}/contacts")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createPartnerContact(@PathVariable Long partnerId,
                                                                  @RequestBody @Valid PartnerContactCreateDto dto) {
        partnerContactService.createPartnerContact(partnerId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.created")));
    }

    @PutMapping("/partner-contacts/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> updatePartnerContact(@PathVariable Long id,
                                                                  @RequestBody @Valid PartnerContactUpdateDto dto) {
        partnerContactService.updatePartnerContact(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.updated")));
    }

    @DeleteMapping("/partner-contacts")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deletePartnerContact(@RequestBody List<Long> ids) {

        partnerContactService.deletePartnerContacts(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}
