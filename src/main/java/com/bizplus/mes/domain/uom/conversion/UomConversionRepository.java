package com.bizplus.mes.domain.uom.conversion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UomConversionRepository extends
        JpaRepository<UomConversion, Long>, UomConversionQueryRepository {

    boolean existsByFromUomIdAndToUomId(Long fromUomId, Long toUomId);
}
