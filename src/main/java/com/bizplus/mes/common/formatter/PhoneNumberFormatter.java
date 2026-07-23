package com.bizplus.mes.common.formatter;

public class PhoneNumberFormatter {

    public static String format(String number) {

        if (number == null || number.isBlank()) {
            return "-";
        }

        String tel = number.replaceAll("-", "");

        if (tel.startsWith("010") && tel.length() == 11) {
            return tel.substring(0, 3)
                    + "-"
                    + tel.substring(3, 7)
                    + "-"
                    + tel.substring(7);
        }

        return number;
    }
}
