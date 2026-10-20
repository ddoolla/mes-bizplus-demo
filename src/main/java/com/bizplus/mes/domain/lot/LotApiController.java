package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.lot.dto.LotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lots")
@RequiredArgsConstructor
public class LotApiController {

    private final LotService lotService;

    @GetMapping
    public ResponseEntity<ApiResponse<LotDto>> readByLotNo(@RequestParam String lotNo) {
        return ResponseEntity.ok(ApiResponse.success(lotService.getByLotNo(lotNo)));
    }
}
