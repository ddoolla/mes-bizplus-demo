package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BomService {

    BomListDto getBoms(BomSearchDto dto, Pageable pageable);

    List<BomDto> getBoms(Long itemId);

    BomDto getBom(Long id);

    Optional<BomDto> getPrimaryBom(Long itemId);

    boolean checkCode(Long id, String code);

    Long createBom(BomCreateDto dto);

    void updateBom(Long id, BomUpdateDto dto);

    void deleteBoms(List<Long> ids);
}
