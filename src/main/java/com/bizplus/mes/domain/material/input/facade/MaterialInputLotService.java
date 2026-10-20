package com.bizplus.mes.domain.material.input.facade;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.material.input.dto.MaterialInputLotDto;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.conversion.UomConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MaterialInputLotService {

    private final UomConversionService uomConversionService;

    private final UomReader uomReader;
    private final LotReader lotReader;
    private final InventoryReader inventoryReader;

    @Transactional
    public MaterialInputLotDto getByLotNo(String lotNo, Long inputUomId) {
        Uom inputUom = uomReader.getById(inputUomId);
        Lot lot = lotReader.getByLotNo(lotNo);
        Item item = lot.getItem();

        Inventory inventory = inventoryReader.get(item.getId(), lot.getId());

        // 재고 단위 -> 투입 단위 환산
        BigDecimal convertedQuantity = uomConversionService.convert(
                inventory.getQuantity(),
                item.getUom().getId(),
                inputUom.getId()
        );

        return new MaterialInputLotDto(
                lot.getId(),
                lot.getNo(),
                item.getId(),
                convertedQuantity,
                inputUom.getCode()
        );
    }
}
