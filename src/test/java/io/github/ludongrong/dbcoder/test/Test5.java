package io.github.ludongrong.dbcoder.test;

import io.github.ludongrong.dbcoder.excel.OneLIfeCycleUUID;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Test5 {

    public static void main(String[] args) {
        OneLIfeCycleUUID oneLIfeCycleUUID = new OneLIfeCycleUUID();
        IntStream.range(0, 1000).forEach(e->{
            System.out.println(oneLIfeCycleUUID.randomUUID7());
        });

        Map<String, Object> cache = new HashMap<>(1000);
        System.out.println(cache.containsKey("1"));
        cache.put("1", null);
        System.out.println(cache.containsKey("1"));
    }
}
