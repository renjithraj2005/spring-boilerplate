package com.boilerplate.demo.helper.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CollectionHelperTest {

    @Test
    public void test_map() throws Exception {


        TestDto obj1 = obj("key1");
        TestDto obj2 = obj("key2");
        TestDto obj3 = obj("key1");
        List<TestDto> l = Arrays.asList(obj1, obj2, obj3);

        Map<String, TestDto> map = CollectionHelper.mapStraight(l, v -> v.field);

        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals(obj1, map.get("key1"));
        Assertions.assertEquals(obj2, map.get("key2"));

    }
    @Test
    public void test_map2() throws Exception {

        TestDto obj1 = obj("key1", "value1");
        TestDto obj2 = obj("key2", "value2");
        TestDto obj3 = obj("key1", "value3");
        List<TestDto> l = Arrays.asList(obj1, obj2, obj3);

        Map<String, String> map = CollectionHelper.map(l, v -> v.field, v -> v.field2);

        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals("value1", map.get("key1"));
        Assertions.assertEquals("value2", map.get("key2"));

    }

    private TestDto obj(String field) {
        return obj(field, null);
    }

    private TestDto obj(String field, String field2) {
        TestDto obj = new TestDto();
        obj.field = field;
        obj.field2 = field2;
        return obj;
    }

    private static class TestDto {
        private String field;
        private String field2;
    }

}
