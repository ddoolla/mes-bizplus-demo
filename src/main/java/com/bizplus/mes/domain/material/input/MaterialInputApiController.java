package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputLotDto;
import com.bizplus.mes.domain.material.input.facade.MaterialInputCreateService;
import com.bizplus.mes.domain.material.input.facade.MaterialInputLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material-inputs")
@RequiredArgsConstructor
public class MaterialInputApiController {

    private final MaterialInputLotService materialInputLotService;
    private final MaterialInputCreateService materialInputCreateService;

    @GetMapping("/lot")
    public ResponseEntity<ApiResponse<MaterialInputLotDto>> readLotForInput(@RequestParam String lotNo,
                                                                            @RequestParam Long inputUomId) {
        return ResponseEntity.ok(ApiResponse.success(
                materialInputLotService.getByLotNo(lotNo, inputUomId)));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid MaterialInputCreateDto dto) {
        materialInputCreateService.create(dto);

        return ResponseEntity.ok(ApiResponse.success("자재 투입 요청이 등록되었습니다."));
    }

}
