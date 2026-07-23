package com.bizplus.mes.domain.partner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long>, PartnerQueryRepository {

    Optional<Partner> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
