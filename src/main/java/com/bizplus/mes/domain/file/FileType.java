package com.bizplus.mes.domain.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    IMAGE("이미지"),
    DOCUMENT("문서"),
    ETC("기타");

    private final String description;
}
