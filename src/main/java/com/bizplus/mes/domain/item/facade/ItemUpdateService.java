package com.bizplus.mes.domain.item.facade;

import com.bizplus.mes.domain.file.FileService;
import com.bizplus.mes.domain.file.FileStorageType;
import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.ItemService;
import com.bizplus.mes.domain.item.dto.ItemUpdateDto;
import com.bizplus.mes.domain.item.file.ItemFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemUpdateService {

    private final ItemService itemService;
    private final ItemFileService itemFileService;
    private final FileService fileService;

    @Transactional
    public void update(Long itemId, ItemUpdateDto dto) {
        // 품목 정보 업데이트
        itemService.updateItem(itemId, dto);

        if (dto.getItemFiles() == null || dto.getItemFiles().isEmpty()) {
            return;
        }

        dto.getItemFiles().forEach(itemFile -> {
            MultipartFile multipartFile = itemFile.getMultipartFile();

            if (multipartFile == null || multipartFile.isEmpty()) {
                return;
            }

            // 기존 이미지 있으면 삭제
            if (itemFile.getId() != null && itemFile.getFileId() != null) {
                itemFileService.deleteItemFile(itemFile.getId());
                fileService.deleteFile(itemFile.getFileId());
            }

            // 새로운 이미지 파일 추가
            Long newFileId = fileService.uploadFile(
                    multipartFile,
                    FileStorageType.ITEM_IMAGE
            );

            itemFileService.createItemFile(
                    itemId,
                    newFileId,
                    FileType.IMAGE,
                    itemFile.getSortOrder()
            );
        });
    }
}
