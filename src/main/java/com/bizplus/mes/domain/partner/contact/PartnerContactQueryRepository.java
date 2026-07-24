package com.bizplus.mes.domain.partner.contact;

import java.util.List;
import java.util.Optional;

public interface PartnerContactQueryRepository {

    List<PartnerContact> findPartnerContacts(Long partnerId);

    Optional<PartnerContact> findPartnerContact(Long id);
}
