package com.bizplus.mes.domain.item.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ItemFileCreateDto {

    private Integer sortOrder;
    private MultipartFile multipartFile;
}
