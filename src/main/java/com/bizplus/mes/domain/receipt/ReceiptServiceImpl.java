package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.receipt.dto.ReceiptCreateDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptListDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final PartnerReader partnerReader;
    private final ReceiptReader receiptReader;

    @Override
    public ReceiptListDto getAll(ReceiptSearchDto dto, Pageable pageable) {
        Page<ReceiptDto> page = receiptRepository.fetchReceipts(dto, pageable);

        return new ReceiptListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public ReceiptDto getById(Long id) {
        return receiptRepository.fetchReceipt(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RECEIPT_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }

    @Override
    public Long create(ReceiptCreateDto dto) {
        Partner partner = partnerReader.getById(dto.getPartnerId());

        LocalDate today = LocalDate.now();

        return receiptRepository.save(new Receipt(
                        partner,
                        CodeGenerator.generate(
                                CodePrefix.RECEIPT,
                                today,
                                receiptRepository.findMaxReceiptNo(today)
                        ),
                        today,
                        ReceiptStatus.DRAFT,
                        dto.getRemark()
                ))
                .getId();
    }

    @Transactional
    @Override
    public void deleteAll(List<Long> ids) {
        ids.forEach(id -> receiptReader.getById(id).delete());
    }
}
