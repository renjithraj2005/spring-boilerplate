package com.boilerplate.demo.helper.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    private  static final NumberFormat df = NumberFormat.getInstance(Locale.FRENCH);
    public static final Double MAX_VALUE = 10000000000000.00;
    public static final Double MIN_VALUE = -10000000000000.00;

    /**
     *
     * @param amt the amount
     * @return for example -1234,56
     */
    public static String printAmount(Double amt) {
        if (amt == null) {
            return StringUtils.EMPTY;
        }
        String s = df.format(amt);
        return StringUtils.remove(s, "\u00a0");
    }

    public static BigDecimal add(BigDecimal bd, Double d) {
        if (d == null) {
            return bd;
        }
        return bd.add(BigDecimal.valueOf(d));
    }
}
