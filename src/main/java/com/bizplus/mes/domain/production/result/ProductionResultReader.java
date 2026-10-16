package com.bizplus.mes.domain.production.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductionResultReader {

    private final ProductionResultRepository productionResultRepository;
}
