package com.bizplus.mes.domain.partner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long>, PartnerQueryRepository {

    Optional<Partner> findByIdAndDeletedAtIsNull(Long id);

    Optional<Partner> findByCodeAndDeletedAtIsNull(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

}
