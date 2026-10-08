package com.bizplus.mes.domain.lot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class LotNoGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final LotRepository lotRepository;

    public String generate(String prefix, LocalDate date) {
        String maxLotNo = lotRepository.findMaxLotNo(date);
        String datePart = date.format(DATE_FORMATTER);

        if (maxLotNo == null) {
            return String.format("%s-%s-0001", prefix, datePart);
        }

        int lastIndex = maxLotNo.lastIndexOf("-");

        if (lastIndex == -1) {
            throw new IllegalStateException("로트 형식이 올바르지 않습니다.");
        }

        int nextNumber = Integer.parseInt(
                maxLotNo.substring(lastIndex + 1)
        ) + 1;

        return String.format("%s-%s-%04d", prefix, datePart, nextNumber);
    }
}
