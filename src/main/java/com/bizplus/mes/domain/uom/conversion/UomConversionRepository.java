package com.bizplus.mes.domain.uom.conversion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UomConversionRepository extends
        JpaRepository<UomConversion, Long>, UomConversionQueryRepository {

    Optional<UomConversion> findByFromUomIdAndToUomId(Long fromUomId, Long toUomId);

    boolean existsByFromUomIdAndToUomId(Long fromUomId, Long toUomId);
}
