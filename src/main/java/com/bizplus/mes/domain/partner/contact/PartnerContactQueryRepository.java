package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.partner.contact.dto.PartnerContactDto;

import java.util.List;
import java.util.Optional;

public interface PartnerContactQueryRepository {

    List<PartnerContactDto> findPartnerContacts(Long partnerId);

    Optional<PartnerContactDto> findPartnerContact(Long id);
}
