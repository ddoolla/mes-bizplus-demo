package com.bizplus.mes.domain.item.facade;

import com.bizplus.mes.domain.file.FileService;
import com.bizplus.mes.domain.file.FileStorageType;
import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.ItemService;
import com.bizplus.mes.domain.item.dto.ItemCreateDto;
import com.bizplus.mes.domain.item.file.ItemFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemCreateService {

    private final ItemService itemService;
    private final ItemFileService itemFileService;
    private final FileService fileService;

    @Transactional
    public void create(ItemCreateDto dto) {
        Long newItemId = itemService.createItem(dto);

        if (dto.getImageFiles() == null) {
            return;
        }

        int sortOrder = 1;

        for (MultipartFile imageFile : dto.getImageFiles()) {
            if (imageFile.isEmpty()) {
                continue;
            }

            Long newFileId = fileService.upload(
                    imageFile,
                    FileStorageType.ITEM_IMAGE
            );

            itemFileService.createItemFile(
                    newItemId,
                    newFileId,
                    FileType.IMAGE,
                    sortOrder
            );

            sortOrder++;
        }
    }
}
