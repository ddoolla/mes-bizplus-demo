package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.FileDto;
import com.bizplus.mes.domain.file.dto.FileResourceDto;
import org.springframework.web.multipart.MultipartFile;

/*
 * 파일 서비스 (DB + 실제 파일 조작)
 * */
public interface FileService {


    /**
     * 파일 정보 DB 저장 + 실제 파일 저장
     *
     * @param multipartFile - 업로드 파일
     * @param storageType   - root-path 다음에 올 저장 경로
     * @return DB에 새로 저장된 파일 ID
     */
    Long upload(MultipartFile multipartFile, FileStorageType storageType);

    /**
     * DB에 저장된 파일 정보 가져오기
     *
     * @param id - 파일 엔터티 아이디
     * @return DB에 저장된 파일 정보
     */
    FileDto getFileInfo(Long id);

    /**
     * 파일 메타 정보 + 실제 파일 리소스 가져오기
     *
     * @param id - 파일 엔터티 아이디
     * @return - 파일 메타 정보 + 실제 파일 리소스
     */
    FileResourceDto getFileResource(Long id);

    /**
     * 파일 삭제
     * DB - 논리 삭제
     * 파일 - 삭제 예정 디렉토리로 이동
     *
     * @param id - 파일 엔터티 아이디
     */
    void delete(Long id);
}
