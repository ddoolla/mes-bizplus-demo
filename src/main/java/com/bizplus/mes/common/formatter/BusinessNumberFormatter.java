package com.bizplus.mes.common.formatter;

public class BusinessNumberFormatter {

    public static String format(String number) {

        if (number == null || number.isBlank()) {
            return "-";
        }

        String no = number.replaceAll("-" ,"");

        if (no.length() != 10) {
            return number;
        }

        return no.substring(0, 3)
                + "-"
                + no.substring(3, 5)
                + "-"
                + no.substring(5);
    }
}
