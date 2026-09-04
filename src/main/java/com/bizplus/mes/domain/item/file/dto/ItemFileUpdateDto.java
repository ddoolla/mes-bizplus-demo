package com.bizplus.mes.domain.item.file.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ItemFileUpdateDto {

    @NotNull
    private Long id;
    private MultipartFile imageFile;
}
