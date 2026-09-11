package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.StoredFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileUploadProperties properties;

    public StoredFileDto storeFile(MultipartFile multipartFile, FileStorageType storageType, String subPath) {
        String originalName = multipartFile.getOriginalFilename();
        String extension = getExtension(originalName);
        String storedName = UUID.randomUUID() + "." + extension;

        LocalDate today = LocalDate.now();

        // 파일 저장할 디렉토리 상대 경로
        Path relativePath = Paths.get(storageType.getPath());

        if (subPath != null && !subPath.isBlank()) {
            relativePath = relativePath.resolve(subPath);
        }

        relativePath = relativePath
                .resolve(String.valueOf(today.getYear()))
                .resolve(String.format("%02d", today.getMonthValue()));

        // 파일 저장할 디렉토리 절대 경로 (root-path + 상대 경로)
        Path directory = Paths.get(properties.getRootPath())
                .resolve(relativePath);

        try {
            // 디렉토리 생성
            Files.createDirectories(directory);

            // 실제 파일 절대 경로
            Path filePath = directory.resolve(storedName);

            // 파일 저장
            multipartFile.transferTo(filePath);

            // DB에 저장할 파일 상대 경로 (상대 경로 + 파일명)
            String storagePath = relativePath
                    .resolve(storedName)
                    .toString()
                    .replace("\\", "/");

            return new StoredFileDto(
                    originalName,
                    storedName,
                    storagePath,
                    multipartFile.getContentType(),
                    extension,
                    multipartFile.getSize()
            );

        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.", e);
        }
    }

    @Override
    public StoredFileDto store(MultipartFile multipartFile, FileStorageType storageType) {
        return storeFile(multipartFile, storageType, null);
    }

    @Override
    public StoredFileDto store(MultipartFile multipartFile, FileStorageType storageType, String subPath) {
        return storeFile(multipartFile, storageType, subPath);
    }

    private String getExtension(String originalName) {
        if (originalName == null || !originalName.contains(".")) {
            return "";
        }

        return originalName.substring(
                originalName.lastIndexOf(".") + 1);
    }

    @Override
    public Resource load(String storagePath) {
        Path filePath = Paths.get(properties.getRootPath())
                .resolve(storagePath)
                .normalize();

        try {
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new IllegalStateException("파일을 읽을 수 없습니다.", e);
        }
    }

    @Override
    public void move(String sourcePath, String targetPath) {
        Path source = Paths.get(properties.getRootPath())
                .resolve(sourcePath)
                .normalize();

        Path target = Paths.get(properties.getRootPath())
                .resolve(targetPath)
                .normalize();

        try {
            // 디렉토리까지만 생성
            Files.createDirectories(target.getParent());

            Files.move(
                    source,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {
            throw new IllegalStateException("파일 이동에 실패했습니다.", e);
        }
    }

    @Override
    public void delete(String storagePath) {
        Path filePath = Paths.get(properties.getRootPath())
                .resolve(storagePath)
                .normalize();

        try {
            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new IllegalStateException("파일 삭제에 실패했습니다.", e);
        }
    }
}
