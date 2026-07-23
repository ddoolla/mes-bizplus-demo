package com.bizplus.mes.common.formatter;

public class CorporateNumberFormatter {

    public static String format(String number) {

        if (number == null || number.isBlank()) {
            return "-";
        }

        String no = number.replaceAll("-", "");

        if (no.length() != 13)   {
            return number;
        }

        return no.substring(0, 6)
                + "-"
                + no.substring(6);
    }
}
