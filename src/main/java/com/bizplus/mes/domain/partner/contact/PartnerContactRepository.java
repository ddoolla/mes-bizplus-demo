package com.bizplus.mes.domain.partner.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerContactRepository extends
        JpaRepository<PartnerContact, Long>, PartnerContactQueryRepository {

    Optional<PartnerContact> findByIdAndDeletedAtIsNull(Long id);
}
