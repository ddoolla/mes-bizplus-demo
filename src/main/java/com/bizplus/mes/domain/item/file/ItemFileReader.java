package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemFileReader {

    private final ItemFileRepository itemFileRepository;

    public ItemFile getById(Long id) {
        return itemFileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_FILE_NOT_FOUND, "id: " + id));
    }


}
