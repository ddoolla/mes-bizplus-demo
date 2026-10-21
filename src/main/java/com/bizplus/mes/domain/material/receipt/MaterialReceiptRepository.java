package com.bizplus.mes.domain.material.receipt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialReceiptRepository
        extends JpaRepository<MaterialReceipt, Long>, MaterialReceiptQueryRepository {
}
