package com.boilerplate.demo.helper.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CollectionHelper {

    /**
     * Allow duplicated key, take the first found.
     * @param col
     * @param by
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> mapStraight(Collection<V> col, Function<V, K> by) {
        return map(col, by, v -> v);
    }

    public static <K, V, V2> Map<K, V2> map(Collection<V> col, Function<V, K> by, Function<V, V2> to) {
        if (CollectionUtils.isEmpty(col)) {
            return new HashMap<>(0);
        }
        Map<K, V2> resultMap = new HashMap<>(col.size());
        for (V value : col) {
            K key = by.apply(value);
            V2 existingValue = resultMap.get(key);
            if (existingValue == null) {
                resultMap.put(key, to.apply(value));
            }
        }
        return resultMap;
    }
}
