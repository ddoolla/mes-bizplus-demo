package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.domain.file.FileService;
import com.bizplus.mes.domain.item.file.dto.ItemFileDeleteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemFileDeleteService {

    private final FileService fileService;
    private final ItemFileService itemFileService;

    @Transactional
    public void delete(ItemFileDeleteDto dto) {
        itemFileService.deleteItemFile(dto.getId());
        fileService.deleteFile(dto.getFileId());
    }
}
