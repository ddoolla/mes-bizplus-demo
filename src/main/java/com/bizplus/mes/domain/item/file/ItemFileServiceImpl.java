package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.file.File;
import com.bizplus.mes.domain.file.FileReader;
import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.item.file.dto.ItemFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFileServiceImpl implements ItemFileService {

    private final ItemFileRepository itemFileRepository;

    private final ItemFileReader itemFileReader;
    private final ItemReader itemReader;
    private final FileReader fileReader;

    @Override
    public List<ItemFileDto> getItemFiles(Long itemId, FileType fileType) {
        return itemFileRepository.findItemFiles(itemId, fileType);
    }

    @Override
    public ItemFileDto getItemFile(Long id) {
        return itemFileRepository.findItemFile(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_FILE_NOT_FOUND, "id: " + id));
    }

    @Transactional
    @Override
    public void createItemFile(Long itemId,
                               Long fileId,
                               FileType fileType,
                               Integer sortOrder) {
        Item item = itemReader.getById(itemId);
        File file = fileReader.getById(fileId);

        itemFileRepository.save(
                new ItemFile(item, file, fileType, sortOrder)
        );
    }

    @Override
    public void updateItemFile(Long id, Long fileId) {
        ItemFile itemFile = itemFileReader.getById(id);
        File file = fileReader.getById(fileId);

        itemFile.updateFile(file);
    }

    @Override
    public void deleteItemFile(Long id) {
        itemFileRepository.deleteById(id);
    }
}
