package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.receipt.dto.ReceiptCreateDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptListDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import com.bizplus.mes.domain.sequence.CodeGenerator;
import com.bizplus.mes.domain.sequence.CodePrefix;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final PartnerReader partnerReader;
    private final ReceiptReader receiptReader;

    private final CodeGenerator codeGenerator;

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

        String newReceiptNo = codeGenerator.generate(CodePrefix.RECEIPT, dto.getDate());

        return receiptRepository.save(new Receipt(
                        partner,
                        newReceiptNo,
                        dto.getDate(),
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
