package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.domain.production.result.dto.ProductionResultCreateDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultListDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultSearchDto;
import org.springframework.data.domain.Pageable;

public interface ProductionResultService {

    ProductionResultListDto getAll(ProductionResultSearchDto dto, Pageable pageable);

    void create(ProductionResultCreateDto dto);
}
