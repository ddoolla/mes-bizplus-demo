package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UomConversionApiController {

    private final UomConversionReader uomConversionReader;

    @GetMapping("/api/convertible-uoms")
    public ResponseEntity<ApiResponse<List<ConvertibleUomDto>>> readConvertibleUoms(@RequestParam Long toUomId) {
        return ResponseEntity.ok(
                ApiResponse.success(uomConversionReader.getConvertibleUoms(toUomId)));
    }
}
