package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomDto;
import com.bizplus.mes.domain.bom.dto.BomSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BomQueryRepository {

    Page<BomDto> findBoms(BomSearchDto dto, Pageable pageable);

    List<BomDto> findBoms(Long itemId);

    Optional<BomDto> findBom(Long id);

    Optional<BomDto> findPrimaryBom(Long itemId);

    void resetPrimary(Long itemId);
}
