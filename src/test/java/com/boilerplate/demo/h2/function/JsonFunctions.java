package com.boilerplate.demo.h2.function;


import com.boilerplate.demo.helper.utils.StringHelper;

public class JsonFunctions {

    public static Integer jsonExtract(String jsonString, String key) {
        return com.jayway.jsonpath.JsonPath.parse(StringHelper.unescapeJson(jsonString)).read(key);


    }
}
