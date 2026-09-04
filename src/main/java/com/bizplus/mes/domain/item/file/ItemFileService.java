package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.file.dto.ItemFileDto;

import java.util.List;

public interface ItemFileService {

    List<ItemFileDto> getItemFiles(Long itemId, FileType fileType);

    ItemFileDto getItemFile(Long id);

    void createItemFile(Long itemId,
                        Long fileId,
                        FileType fileType,
                        Integer sortOrder);

    void updateItemFile(Long id, Long fileId);

    void deleteItemFile(Long id);
}
