package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.domain.receipt.dto.ReceiptCreateDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptListDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptService {

    ReceiptListDto getAll(ReceiptSearchDto dto, Pageable pageable);

    ReceiptDto getById(Long id);

    Long create(ReceiptCreateDto dto);

    void deleteAll(List<Long> ids);
}
