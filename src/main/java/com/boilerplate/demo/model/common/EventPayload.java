package com.boilerplate.demo.model.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class EventPayload extends LinkedHashMap<String, Object> {
    public EventPayload() {
        super();
    }
    public EventPayload(EventPayload p) {
        super(p);
    }
    public EventPayload(Map<String, Object> m) {
        super(m);
    }


    public EventPayload with(String key, Object value) {
        put(key, value);
        return this;
    }

    public static EventPayload map(String key, Object value) {
        return new EventPayload().with(key, value);
    }
}
