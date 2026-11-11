package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotValidator {

    public void validate(Item item, Lot lot) {
        if (!item.isLotManaged()) {
            if (lot != null) {
                throw new IllegalArgumentException("LOT 미관리 품목은 LOT를 지정할 수 없습니다.");
            }

            return;
        }

        if (lot == null) {
            throw new IllegalArgumentException("LOT 관리 품목은 LOT가 필수입니다.");
        }

        if (!lot.getItem().getId().equals(item.getId())) {
            throw new IllegalArgumentException("해당 품목의 LOT가 아닙니다.");
        }
    }
}
