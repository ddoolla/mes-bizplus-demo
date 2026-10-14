package com.bizplus.mes.domain.work.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkResultReader {

    private final WorkResultRepository workResultRepository;
}
