package com.bizplus.mes.common.util;

import com.bizplus.mes.common.formatter.BusinessNumberFormatter;
import com.bizplus.mes.common.formatter.CorporateNumberFormatter;
import com.bizplus.mes.common.formatter.PhoneNumberFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
 * 타임리프에서 활용
 * ex) ${@format.businessNo(...)}
 * */
@Component("format")
public class FormatUtils {

    public String value(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }

        return value;
    }

    public String decimalValue(BigDecimal value) {
        if (value == null) {
            return "-";
        }

        return value.stripTrailingZeros().toPlainString();
    }

    public String nameWithCode(String code, String name) {
        boolean hasCode = code != null && !code.isBlank();
        boolean hasName = name != null && !name.isBlank();

        if (hasCode && hasName) {
            return name + " [" + code + "]";
        }

        if (hasName) {
            return name;
        }

        if (hasCode) {
            return code;
        }

        return "-";
    }

    public String businessNo(String value) {
        return BusinessNumberFormatter.format(value);
    }

    public String corporateNo(String value) {
        return CorporateNumberFormatter.format(value);
    }

    public String phoneNo(String value) {
        return PhoneNumberFormatter.format(value);
    }
}
