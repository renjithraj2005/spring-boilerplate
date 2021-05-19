package com.boilerplate.demo.helper.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static final Map<String, String> SPECIAL_ZIPCODE_MAPPING;
    static {
        SPECIAL_ZIPCODE_MAPPING = new HashMap<>(2);
        SPECIAL_ZIPCODE_MAPPING.put("200", "2A");
        SPECIAL_ZIPCODE_MAPPING.put("202", "2B");
    }

    public static <T> T nullSafe(T obj, T defaultObj) {
        return obj != null ? obj : defaultObj;
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            Collections.sort(list);
        }
    }

    public static String translateZipcode(String zipcode) {
        if (StringUtils.isBlank(zipcode)) {
            return StringUtils.EMPTY;
        }
        String translatedZipcode = SPECIAL_ZIPCODE_MAPPING.get(zipcode);
        return translatedZipcode != null ? translatedZipcode : zipcode;
    }

    public static String randomToken() {
        String random = RandomStringUtils.randomAlphanumeric(32) +
                StringUtils.remove(UUID.randomUUID().toString(), "-");
        return random;
    }
}
