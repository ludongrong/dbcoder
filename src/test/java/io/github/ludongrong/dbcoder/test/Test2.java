package io.github.ludongrong.dbcoder.test;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.StringTokenizer;
import java.util.UUID;

public class Test2 {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        }
    }
}
