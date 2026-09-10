package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.FileDto;
import com.bizplus.mes.domain.file.dto.FileResourceDto;
import com.bizplus.mes.domain.file.dto.StoredFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    private final FileReader fileReader;

    @Transactional
    @Override
    public Long storeFile(MultipartFile multipartFile, FileStorageType storageType) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }

        // 실제 파일 저장
        StoredFileDto storedFile = fileStorageService.store(multipartFile, storageType);

        try {
            // 파일 정보 DB 저장
            return fileRepository.save(FileMapper.toEntity(storedFile)).getId();

        } catch (Exception e) {
            // DB 저장 실패 시 파일 삭제
            fileStorageService.delete(
                    storedFile.getStoragePath()
            );

            throw e;
        }
    }

    @Override
    public FileDto getFileInfo(Long id) {
        return FileMapper.toDto(fileReader.getById(id));
    }

    @Override
    public FileResourceDto getFileResource(Long id) {
        File file = fileReader.getById(id);
        Resource resource = fileStorageService.load(file.getStoragePath());

        return FileMapper.toResourceDto(file, resource);
    }

    @Override
    public Resource getFilesAsZip(List<Long> ids) {

        try {
            Path zipPath = Files.createTempFile("files-", ".zip");

            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {

                for (Long id : ids) {
                    File file = fileReader.getById(id);

                    Path filePath = Paths.get(file.getStoragePath(), file.getStoredName());

                    if (!Files.exists(filePath)) {
                        continue;
                    }

                    ZipEntry zipEntry = new ZipEntry(file.getOriginalName());

                    zos.putNextEntry(zipEntry);

                    Files.copy(filePath, zos);

                    zos.closeEntry();
                }
            }

            return new FileSystemResource(zipPath);

        } catch (IOException e) {
            throw new RuntimeException("ZIP 파일 생성에 실패했습니다.", e);
        }
    }

    @Transactional
    @Override
    public void deleteFile(Long id) {
        File file = fileReader.getById(id);

        String targetPath = "deleted/" + file.getStoragePath();

        // 실제 파일 - 삭제 예정 디렉토리로 이동 (실제 삭제 X)
        fileStorageService.move(file.getStoragePath(), targetPath);

        // DB - 논리 삭제, 경로 변경
        file.changeStoragePath(targetPath);
        file.delete();
    }
}