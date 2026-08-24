package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessMaterialReader {

    private final ProcessMaterialRepository processMaterialRepository;

    public ProcessMaterial getById(Long id) {
        return processMaterialRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROCESS_MATERIAL_NOT_FOUND, "id: " + id));
    }
}
