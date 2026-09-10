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

        if (dto.getItemFiles() == null || dto.getItemFiles().isEmpty()) {
            return;
        }

        dto.getItemFiles().forEach(itemFile -> {
            MultipartFile multipartFile = itemFile.getMultipartFile();

            if (multipartFile.isEmpty()) {
                return;
            }

            Long newFileId = fileService.storeFile(
                    multipartFile,
                    FileStorageType.ITEM_IMAGE
            );

            itemFileService.createItemFile(
                    newItemId,
                    newFileId,
                    FileType.IMAGE,
                    itemFile.getSortOrder()
            );
        });
    }
}
