package com.bizplus.mes.domain.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
* 파일 유형별 상대 경로 저장
* */
@Getter
@RequiredArgsConstructor
public enum FileStorageType {

    ITEM_IMAGE("item/image");

    private final String path;
}
