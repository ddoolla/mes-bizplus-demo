package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;

public interface MaterialInputService {

    void create(Long workOrderId, MaterialInputCreateDto dto);
}
