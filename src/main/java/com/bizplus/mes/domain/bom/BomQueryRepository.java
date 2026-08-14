package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomDto;
import com.bizplus.mes.domain.bom.dto.BomSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BomQueryRepository {

    Page<BomDto> findBoms(BomSearchDto dto, Pageable pageable);

    Optional<BomDto> findBom(Long id);

    Integer findNextRevisionNo(Long itemId);
}
