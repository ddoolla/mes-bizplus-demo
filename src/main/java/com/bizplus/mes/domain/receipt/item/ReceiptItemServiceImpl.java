package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.receipt.ReceiptReader;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemCreateDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemEditDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemUpdateDto;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.conversion.UomConversionReader;
import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptItemServiceImpl implements ReceiptItemService {

    private final ReceiptItemRepository receiptItemRepository;

    private final UomConversionReader uomConversionReader;
    private final UomReader uomReader;
    private final ItemReader itemReader;
    private final ReceiptReader receiptReader;
    private final ReceiptItemReader receiptItemReader;

    @Override
    public List<ReceiptItemDto> getAll(Long receiptId) {
        return receiptItemRepository.fetchReceiptItems(receiptId);
    }

    @Override
    public List<ReceiptItemEditDto> getAllForEdit(Long receiptId) {
        return receiptItemRepository.fetchReceiptItems(receiptId).stream()
                .map(dto -> {
                    List<ConvertibleUomDto> convertibleUoms = uomConversionReader
                            .getConvertibleUoms(dto.getItem().uom().id());

                    return new ReceiptItemEditDto(dto, convertibleUoms);
                })
                .toList();
    }

    @Transactional
    @Override
    public void create(Long receiptId, ReceiptItemCreateDto dto) {
        Receipt receipt = receiptReader.getById(receiptId);

        dto.getItemIds().forEach(id -> {
            Item item = itemReader.getById(id);

            receiptItemRepository.save(new ReceiptItem(
                    receipt,
                    item,
                    item.getUom(),
                    null,
                    null,
                    null
            ));
        });
    }

    @Override
    public void update(List<ReceiptItemUpdateDto> dtoList) {
        dtoList.forEach(dto -> {
            ReceiptItem receiptItem = receiptItemReader.getById(dto.getId());
            Uom uom = uomReader.getById(dto.getUomId());

            receiptItem.update(
                    uom,
                    dto.getQuantity(),
                    dto.getRemark()
            );
        });
    }
}
