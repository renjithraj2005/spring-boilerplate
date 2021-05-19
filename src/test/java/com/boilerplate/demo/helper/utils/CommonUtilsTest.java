package com.boilerplate.demo.helper.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommonUtilsTest {

    @Test
    public void test_translateZipcode() throws Exception {
        Assertions.assertEquals("21", CommonUtils.translateZipcode("21"));
        Assertions.assertEquals("2A", CommonUtils.translateZipcode("200"));
        Assertions.assertEquals("2B", CommonUtils.translateZipcode("202"));
    }
}
