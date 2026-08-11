package com.bizplus.mes.domain.uom;

import com.bizplus.mes.domain.uom.dto.UomCreateDto;
import com.bizplus.mes.domain.uom.dto.UomDto;
import com.bizplus.mes.domain.uom.dto.UomUpdateDto;

import java.util.List;

public interface UomService {

    List<UomDto> getUoms(String code, String name);

    UomDto getUom(Long id);

    /**
     * 논리 삭제된 코드도 중복으로 간주
     */
    boolean checkCode(Long id, String code);

    void createUom(UomCreateDto dto);

    void updateUom(Long id, UomUpdateDto dto);

    void deleteUoms(List<Long> ids);
}
