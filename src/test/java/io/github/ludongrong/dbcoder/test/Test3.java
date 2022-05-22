package io.github.ludongrong.dbcoder.test;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.StringTokenizer;
import java.util.UUID;

public class Test3 {

    private static String tamplate = "INSERT INTO T_GROUP_PARAMETER_RESONE(ID, ORDER_SOURCE, ONE_LEVEL_REASON, TWO_LEVEL_REASON)VALUES(''{0}'',''{1}'',''{2}'',''{3}'');";

    public static void main(String[] args) {

        String readPath = "C:\\Users\\ldr\\Desktop\\read.txt";
        String writePath = "C:\\Users\\ldr\\Desktop\\write.txt";

        String content = FileUtil.readString(readPath, Charset.defaultCharset());
        String msg = "";

        StringTokenizer st = new StringTokenizer(content, "\r\n");
        while (st.hasMoreTokens()) {
            String[] items = st.nextToken().split(",");
            msg += MessageFormat.format(tamplate, UUID.randomUUID().toString().replaceAll("-",""), items[0], items[1], items[2]);
            msg += "\r\n";
        }

        FileUtil.writeString(msg, writePath, Charset.defaultCharset());
    }
}
