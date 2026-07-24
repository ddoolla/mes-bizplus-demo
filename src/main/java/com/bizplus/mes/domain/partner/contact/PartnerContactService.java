package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.partner.contact.dto.PartnerContactCreateDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactUpdateDto;

import java.util.List;

public interface PartnerContactService {

    List<PartnerContactDto> getPartnerContacts(Long partnerId);

    PartnerContactDto getPartnerContact(Long id);

    void createPartnerContact(Long partnerId, PartnerContactCreateDto dto);

    void updatePartnerContact(Long id, PartnerContactUpdateDto dto);

    void deletePartnerContacts(List<Long> ids);
}
