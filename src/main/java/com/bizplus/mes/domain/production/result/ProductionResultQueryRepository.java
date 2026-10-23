package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.domain.production.result.dto.ProductionResultDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductionResultQueryRepository {

    Page<ProductionResultDto> fetchProductionResults(ProductionResultSearchDto dto, Pageable pageable);
}
