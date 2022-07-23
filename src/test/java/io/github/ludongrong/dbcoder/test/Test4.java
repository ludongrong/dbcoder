package io.github.ludongrong.dbcoder.test;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import oracle.jdbc.OracleTypes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Test4 {

    public static void main(String[] args) {

//        Field[] fields = ReflectUtil.getFields(OracleTypes.class);
//        for (Field field: fields) {
//            System.out.println(String.format("_ORACLE_SQL_TYPE.put(\"%s\", OracleTypes.%s);", field.getName(), field.getName()));
//        }
        UUID uuid = UUID.nameUUIDFromBytes("1234567890asdfghjklzxcvbnmqwertyuiop".getBytes());
        System.out.println(uuid.toString());
        System.out.println(uuid.toString(true));

        System.out.println("-------");

        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.fastUUID());
        System.out.println(IdUtil.objectId());
        System.out.println("dfsdf_dsfsd_".replaceAll("_","-"));

        System.out.println("-------");
        System.out.println(IdUtil.randomUUID());
        System.out.println(IdUtil.simpleUUID());

        Map<String, Object> cache = new HashMap<>(1000);
        IntStream.range(0, 1000).forEach(e->{
            String key = IdUtil.randomUUID().substring(0, 7);
//            System.out.println(key);
            cache.putIfAbsent(key, key);
        });

        System.out.println(cache.size());
    }
}
