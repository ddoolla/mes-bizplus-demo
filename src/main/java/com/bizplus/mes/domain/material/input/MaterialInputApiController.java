package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;
import com.bizplus.mes.domain.material.input.facade.MaterialInputCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material-inputs")
@RequiredArgsConstructor
public class MaterialInputApiController {

    private final MaterialInputCreateService materialInputCreateService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid MaterialInputCreateDto dto) {
        materialInputCreateService.create(dto);

        return ResponseEntity.ok(ApiResponse.success("자재 투입 요청이 등록되었습니다."));
    }

}
