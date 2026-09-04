package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.StoredFileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/*
* 실제 파일 조작 서비스
* */
public interface FileStorageService {

    /**
     * 파일 저장
     * 저장 경로: root-path/storagePath/yyyy/MM/stored-file-name.xxx
     * @param multipartFile - 업로드 파일
     * @param storageType - 유형별 저장 경로
     * @return 저장된 파일 메타 정보
     */
    StoredFileDto store(MultipartFile multipartFile, FileStorageType storageType);

    /**
     * 저장된 파일 리소스 객체로 로드
     * @param storagePath - DB에 저장된 파일 경로 (상대 경로)
     * @return 파일 리소스 객체
     */
    Resource load(String storagePath);

    /**
     * 파일 위치 이동
     * @param sourcePath - 현재 파일 경로 (상대 경로)
     * @param targetPath - 이동시킬 파일 경로 (상대 경로)
     */
    void move(String sourcePath, String targetPath);

    /**
     * 실제 파일 물리 삭제
     * @param storagePath - DB에 저장된 파일 경로 (상대 경로)
     */
    void delete(String storagePath);
}
