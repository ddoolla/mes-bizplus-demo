package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.item.file.dto.ItemFileDeleteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemFileController {

    private final ItemFileDeleteService itemFileDeleteService;
    private final MessageService messageService;

    @DeleteMapping("/item-files")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteItemFile(@RequestBody @Valid ItemFileDeleteDto dto) {
        itemFileDeleteService.delete(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}
