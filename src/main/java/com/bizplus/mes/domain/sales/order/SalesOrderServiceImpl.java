package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderCreateDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderListDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderSearchDto;
import com.bizplus.mes.domain.sequence.CodeGenerator;
import com.bizplus.mes.domain.sequence.CodePrefix;
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

    private final CodeGenerator codeGenerator;

    @Override
    public SalesOrderListDto getSalesOrders(SalesOrderSearchDto dto, Pageable pageable) {
        Page<SalesOrderDto> salesOrderPage = salesOrderRepository.findSalesOrders(dto, pageable);

        return new SalesOrderListDto(salesOrderPage.getContent(), Pagination.of(salesOrderPage));
    }

    @Override
    public List<SalesOrderDto> getAllByPartnerId(Long partnerId) {
        return salesOrderRepository.fetchSalesOrders(partnerId);
    }

    @Override
    public SalesOrderDto getSalesOrder(Long id) {
        return salesOrderRepository.findSalesOrder(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SALES_ORDER_NOT_FOUND, "id: " + id));
    }

    @Override
    public Long createSalesOrder(SalesOrderCreateDto dto) {
        Partner partner = partnerReader.getById(dto.getPartnerId());

        String nextOrderNo = codeGenerator.generate(
                CodePrefix.SALES_ORDER,
                LocalDate.now()
        );

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
    public void confirmSalesOrder(Long id) {
        SalesOrder salesOrder = salesOrderReader.getById(id);

        if (salesOrder.getStatus() != SalesOrderStatus.DRAFT) {
            throw new IllegalStateException("수주 작성중 상태에서만 확정 가능합니다. id: " + id);
        }

        salesOrder.updateStatus(SalesOrderStatus.CONFIRMED);
    }

    @Transactional
    @Override
    public void deleteSalesOrders(List<Long> ids) {
        ids.forEach(id -> salesOrderReader.getById(id).delete());
    }
}
