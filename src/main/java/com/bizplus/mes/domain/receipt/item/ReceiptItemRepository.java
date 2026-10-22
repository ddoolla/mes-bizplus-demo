package com.bizplus.mes.domain.receipt.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long>, ReceiptItemQueryRepository {

    Optional<ReceiptItem> findByIdAndDeletedAtIsNull(Long id);
}
