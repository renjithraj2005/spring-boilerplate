package com.boilerplate.demo.h2.function;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * misc functions
 *
 * @author linux_china
 */
public class MiscFunctions {

    public static String version() {
        return "5.6.25";
    }

    public static String format(Double value, Integer p) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(p);
        return format.format(value);
    }

    public static String format(Double value, Integer p, String locale) {
        NumberFormat format = NumberFormat.getInstance(new Locale(locale));
        format.setMaximumFractionDigits(p);
        return format.format(value);
    }

    public static Integer connectId() {
        return 1;
    }

    public static String sessionUser() {
        return "SA@127.0.0.1";
    }

    public static String currentRole() {
        return "`r1`@`%`,`r2`@`%`";
    }

}