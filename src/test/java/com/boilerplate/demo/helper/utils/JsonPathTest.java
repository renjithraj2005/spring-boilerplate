package com.boilerplate.demo.helper.utils;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JsonPathTest {

    /**
     * {
     *   "name": "John",
     *   "age": 30,
     *   "salary": 30000.13,
     *   "married": true,
     *   "cars": [
     *     {
     *       "name": "Ford"
     *     },
     *     {
     *       "name": "BMW"
     *     }
     *   ]
     * }
     * @throws Exception
     */
    @Test
    public void test_normalizeFilename() throws Exception {
        String json = "{\n" +
                "  \"name\": \"John\",\n" +
                "  \"age\": 30,\n" +
                "  \"salary\": 30000.13,\n" +
                "  \"married\": true,\n" +
                "  \"cars\": [\n" +
                "    {\n" +
                "      \"name\": \"Ford\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"BMW\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Assertions.assertEquals("John", JsonPath.parse(json).read("$.name"));
        Assertions.assertEquals(Integer.valueOf(30), JsonPath.parse(json).read("$.age"));
        Assertions.assertEquals(Double.valueOf(30000.13), JsonPath.parse(json).read("$.salary"));
        Assertions.assertTrue((Boolean)JsonPath.parse(json).read("$.married"));

        List<Map> cars = JsonPath.parse(json).read("$.cars[*]");
        Assertions.assertEquals(2, cars.size());
        Assertions.assertEquals("Ford", cars.get(0).get("name"));
        Assertions.assertEquals("BMW", cars.get(1).get("name"));

        List<Map>  carNames = JsonPath.parse(json).read("$.cars[*].name");
        Assertions.assertEquals(2, carNames.size());
        Assertions.assertEquals("Ford", carNames.get(0));
        Assertions.assertEquals("BMW", carNames.get(1));


        Object o = JsonPath.parse(StringHelper.unescapeJson("\"{\\\"companyId\\\":\\\"38859638900030\\\"}\"")).read("$.companyId");
        System.out.println(o);

    }

}
