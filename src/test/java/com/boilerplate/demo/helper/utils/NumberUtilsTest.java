package com.boilerplate.demo.helper.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class NumberUtilsTest {

    @Test
    public void test_printAmount() throws Exception {
        Assertions.assertEquals("-5361,3", NumberUtils.printAmount(Double.valueOf("-5361.3")));
        Assertions.assertEquals("-52361,38", NumberUtils.printAmount(Double.valueOf("-52361.38")));
        Assertions.assertEquals("0", NumberUtils.printAmount(Double.valueOf("0")));
        Assertions.assertEquals("1234", NumberUtils.printAmount(Double.valueOf("1234")));
        Assertions.assertEquals("", NumberUtils.printAmount(null));
    }
    @Test
    public void test_add() throws Exception {

        Assertions.assertEquals(0, new BigDecimal("-5361.30").compareTo(
                NumberUtils.add(BigDecimal.ZERO, Double.valueOf("-5361.3"))));
        Assertions.assertEquals(0, new BigDecimal("-5360.30").compareTo(
                NumberUtils.add(new BigDecimal("-5361.30"), Double.valueOf("1"))));
    }

}
