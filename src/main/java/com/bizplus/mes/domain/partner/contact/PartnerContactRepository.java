package com.bizplus.mes.domain.partner.contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerContactRepository extends
        JpaRepository<PartnerContact, Long>, PartnerContactQueryRepository {
}
