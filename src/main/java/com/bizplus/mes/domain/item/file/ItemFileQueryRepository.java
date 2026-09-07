package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.file.dto.ItemFileDto;

import java.util.List;
import java.util.Optional;

public interface ItemFileQueryRepository {

    List<ItemFileDto> findItemFiles(Long itemId, FileType fileType);

    Optional<ItemFileDto> findItemFile(Long id);
}
