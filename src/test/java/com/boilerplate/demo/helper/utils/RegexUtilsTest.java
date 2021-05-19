package com.boilerplate.demo.helper.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexUtilsTest {

    @Test
    public void test_ZipCode() throws Exception{
        Assertions.assertTrue(RegexUtils.isZipCodeValid("75009 Paris"));
        Assertions.assertFalse(RegexUtils.isZipCodeValid("750090 Paris"));
        Assertions.assertFalse(RegexUtils.isZipCodeValid("Paris 75009"));
    }
}
