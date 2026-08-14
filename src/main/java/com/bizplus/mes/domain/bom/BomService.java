package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BomService {

    BomListDto getBoms(BomSearchDto dto, Pageable pageable);

    BomDto getBom(Long id);

    boolean checkCode(Long id, String code);

    Long createBom(BomCreateDto dto);

    void updateBom(Long id, BomUpdateDto dto);

    void deleteBoms(List<Long> ids);
}
