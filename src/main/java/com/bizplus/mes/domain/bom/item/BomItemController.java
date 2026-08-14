package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.bom.item.dto.BomItemCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BomItemController {

    private final BomItemService bomItemService;
    private final MessageService messageService;

    @PostMapping("/boms/{bomId}/items")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> createBomItems(@PathVariable Long bomId,
                                                            @Valid List<BomItemCreateDto> dtos) {
        bomItemService.createBomItems(bomId, dtos);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }
}
