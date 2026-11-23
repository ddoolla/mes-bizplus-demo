package com.bizplus.mes.domain.partner;

import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerQueryRepository {

    Page<Partner> fetchAll(PartnerSearchDto dto, Pageable pageable);

    List<Partner> fetchAll(PartnerSearchDto dto);
}
