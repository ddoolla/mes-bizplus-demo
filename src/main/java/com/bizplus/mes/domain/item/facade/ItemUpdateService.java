package com.bizplus.mes.domain.item.facade;

import com.bizplus.mes.domain.file.FileService;
import com.bizplus.mes.domain.file.FileStorageType;
import com.bizplus.mes.domain.item.ItemService;
import com.bizplus.mes.domain.item.dto.ItemUpdateDto;
import com.bizplus.mes.domain.item.file.ItemFileService;
import com.bizplus.mes.domain.item.file.dto.ItemFileDto;
import com.bizplus.mes.domain.item.file.dto.ItemFileUpdateDto;
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

        for (ItemFileUpdateDto fileDto : dto.getItemFiles()) {
            MultipartFile imageFile = fileDto.getImageFile();

            if (imageFile == null || imageFile.isEmpty()) {
                continue;
            }

            // 기존 파일 삭제
            ItemFileDto itemFile = itemFileService.getItemFile(fileDto.getId());

            fileService.delete(itemFile.getFile().id());

            // 새로운 파일로 변경
            Long newFileId = fileService.upload(imageFile, FileStorageType.ITEM_IMAGE);

            itemFileService.updateItemFile(fileDto.getId(), newFileId);
        }
    }
}
