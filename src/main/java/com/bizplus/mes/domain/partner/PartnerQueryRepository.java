package com.bizplus.mes.domain.partner;

import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerQueryRepository {

    Page<Partner> findPartners(PartnerSearchDto dto, Pageable pageable);
}
