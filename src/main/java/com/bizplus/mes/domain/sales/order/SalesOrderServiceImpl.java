package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.sales.order.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    private final PartnerReader partnerReader;
    private final SalesOrderReader salesOrderReader;

    @Override
    public SalesOrderListDto getSalesOrders(SalesOrderSearchDto dto, Pageable pageable) {
        Page<SalesOrderDto> salesOrderPage = salesOrderRepository.findSalesOrders(dto, pageable);

        return new SalesOrderListDto(salesOrderPage.getContent(), Pagination.of(salesOrderPage));
    }

    @Override
    public SalesOrderDto getSalesOrder(Long id) {
        return salesOrderRepository.findSalesOrder(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SALES_ORDER_NOT_FOUND, "id: " + id));
    }

    @Override
    public Long createSalesOrder(SalesOrderCreateDto dto) {
        Partner partner = partnerReader.getById(dto.getPartnerId());

        LocalDate today = LocalDate.now();

        String nextOrderNo = CodeGenerator.generate(
                CodePrefix.SALES_ORDER,
                today,
                salesOrderRepository.findMaxOrderNo(today));

        return salesOrderRepository.save(
                SalesOrderMapper.toEntity(
                        partner,
                        nextOrderNo,
                        SalesOrderStatus.DRAFT,
                        dto
                )
        ).getId();
    }

    @Transactional
    @Override
    public void updateSalesOrder(Long id, SalesOrderUpdateDto dto) {
        SalesOrder salesOrder = salesOrderReader.getById(id);

        if (salesOrder.getStatus() == SalesOrderStatus.DRAFT) {
            Partner partner = partnerReader.getById(dto.getPartnerId());
            salesOrder.updatePartner(partner);
        }

        SalesOrderMapper.apply(salesOrder, dto);
    }

    @Transactional
    @Override
    public void confirmSalesOrder(Long id) {
        SalesOrder salesOrder = salesOrderReader.getById(id);

        if (salesOrder.getStatus() != SalesOrderStatus.DRAFT) {
            throw new IllegalStateException("수주 작성중 상태에서만 확정 가능합니다.");
        }

        salesOrder.updateStatus(SalesOrderStatus.CONFIRMED);
    }

    @Transactional
    @Override
    public void deleteSalesOrders(List<Long> ids) {
        ids.forEach(id -> salesOrderReader.getById(id).delete());
    }
}
