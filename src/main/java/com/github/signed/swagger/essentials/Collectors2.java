package com.github.signed.swagger.essentials;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Collectors2 {
    public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        BinaryOperator<U> failOnDuplicateKey = (key, value) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", key));
        };
        return Collectors.toMap(keyMapper, valueMapper, failOnDuplicateKey, LinkedHashMap::new);
    }
}