package com.bizplus.mes.domain.item.facade;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.file.FileService;
import com.bizplus.mes.domain.file.FileStorageType;
import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemMapper;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.item.dto.ItemUpdateDto;
import com.bizplus.mes.domain.item.file.ItemFileService;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemUpdateService {

    private final ItemReader itemReader;
    private final CommonCodeReader commonCodeReader;
    private final UomReader uomReader;

    private final ItemFileService itemFileService;
    private final FileService fileService;

    @Transactional
    public void update(Long itemId, ItemUpdateDto dto) {
        // 품목 정보 업데이트
        Item item = itemReader.getById(itemId);
        CommonCode itemCategory = commonCodeReader.getByIdOrNull(dto.getCategoryId());
        Uom uom = uomReader.getById(dto.getUomId());

        if (item.isLotManaged()) {
            item.updateLifeShelf(dto.getShelfLife(), dto.getShelfLifeUnit());
        }

        ItemMapper.apply(item, itemCategory, uom, dto);

        // 품목 이미지 업데이트
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
            Long newFileId = fileService.storeFile(
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
