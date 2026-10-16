package com.bizplus.mes.domain.material.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaterialInputReader {

    private final MaterialInputRepository materialInputRepository;
}
