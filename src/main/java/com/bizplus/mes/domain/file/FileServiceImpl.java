package com.bizplus.mes.domain.file;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.file.dto.FileResourceDto;
import com.bizplus.mes.domain.file.dto.StoredFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    @Override
    public File upload(MultipartFile multipartFile, FileStorageType storageType) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }

        // 실제 파일 저장
        StoredFileDto storedFile = fileStorageService.store(multipartFile, storageType);

        try {
            // 파일 정보 DB 저장
            return fileRepository.save(new File(
                    storedFile.getOriginalName(),
                    storedFile.getStoredName(),
                    storedFile.getStoragePath(),
                    storedFile.getExtension(),
                    storedFile.getContentType(),
                    storedFile.getSize()
            ));

        } catch (Exception e) {
            // DB 저장 실패 시 파일 삭제
            fileStorageService.delete(
                    storedFile.getStoragePath()
            );

            throw e;
        }
    }

    @Override
    public File getFileInfo(Long fileId) {
        return fileRepository.findByIdAndDeletedAtIsNull(fileId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND, "id: " + fileId));
    }

    @Override
    public FileResourceDto getFileResource(Long fileId) {
        File file = getFileInfo(fileId);
        Resource resource = fileStorageService.load(file.getStoragePath());

        return new FileResourceDto(
                resource,
                file.getOriginalName(),
                file.getStoredName(),
                file.getStoragePath(),
                file.getExtension(),
                file.getContentType(),
                file.getSize()
        );
    }

    @Transactional
    @Override
    public void delete(Long fileId) {
        File file = getFileInfo(fileId);

        String targetPath = "deleted/" + file.getStoragePath();

        // 실제 파일 - 삭제 예정 디렉토리로 이동 (실제 삭제 X)
        fileStorageService.move(file.getStoragePath(), targetPath);

        // DB - 논리 삭제, 경로 변경
        file.changeStoragePath(targetPath);
        file.delete();
    }
}