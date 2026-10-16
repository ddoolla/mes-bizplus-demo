package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialInputServiceImpl implements MaterialInputService {

    private final MaterialInputRepository materialInputRepository;

    @Override
    public void create(Long workOrderId, MaterialInputCreateDto dto) {

    }
}
