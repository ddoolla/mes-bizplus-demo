package com.bizplus.mes.domain.file;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileReader {

    private final FileRepository fileRepository;

    public File getById(Long id) {
        return fileRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND, "id: " + id));
    }
}
