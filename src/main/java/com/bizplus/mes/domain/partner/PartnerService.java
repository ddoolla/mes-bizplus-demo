package com.bizplus.mes.domain.partner;

import com.bizplus.mes.domain.partner.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {

    PartnerListDto getPartners(PartnerSearchDto dto, Pageable pageable);

    PartnerDto getPartner(Long id);

    /**
     * 논리 삭제된 코드도 중복으로 간주
     */
    boolean checkPartnerCode(Long id, String code);

    void createPartner(PartnerCreateDto dto);

    void updatePartner(Long id, PartnerUpdateDto dto);

    void deletePartners(List<Long> ids);
}
