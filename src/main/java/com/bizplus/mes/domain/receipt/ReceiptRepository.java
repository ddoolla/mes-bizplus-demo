package com.bizplus.mes.domain.receipt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>, ReceiptQueryRepository {

    Optional<Receipt> findByIdAndDeletedAtIsNull(Long id);
}
