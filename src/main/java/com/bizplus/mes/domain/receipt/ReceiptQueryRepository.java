package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.domain.receipt.dto.ReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ReceiptQueryRepository {

    Page<ReceiptDto> fetchReceipts(ReceiptSearchDto dto, Pageable pageable);

    Optional<ReceiptDto> fetchReceipt(Long id);

    String findMaxReceiptNo(LocalDate date);
}
