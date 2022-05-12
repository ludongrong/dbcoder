package io.github.ludongrong.dbcoder.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.StringTokenizer;
import java.util.UUID;

public class Test1 {

    private static String tamplate = "<o:Column Id=\"o{0}}\">\n" +
            "<a:ObjectID>{1}</a:ObjectID>\n" +
            "<a:Name>{2}</a:Name>\n" +
            "<a:Code>{3}</a:Code>\n" +
            "<a:CreationDate>1650807612</a:CreationDate>\n" +
            "<a:Creator>ldr</a:Creator>\n" +
            "<a:ModificationDate>1650807634</a:ModificationDate>\n" +
            "<a:Modifier>ldr</a:Modifier>\n" +
            "<a:DataType>{4}</a:DataType>\n" +
            "<a:Comment>{5}</a:Comment>\n" +
            "</o:Column>";

    public static void main(String[] args) {

        String readPath = "C:\\Users\\ldr\\Desktop\\新建文本文档.txt";
        String writePath = "C:\\Users\\ldr\\Desktop\\write.txt";

        String content = FileUtil.readString(readPath, Charset.defaultCharset());

        int i = 7;
        String msg = "";

        StringTokenizer st = new StringTokenizer(content, "\r\n");
        while (st.hasMoreTokens()) {
            i++;
            String[] items = st.nextToken().split(",");
            msg += MessageFormat.format(tamplate, i, UUID.randomUUID().toString(), items[0], items[1], items[2], items[3]);
            msg += "\r\n";
        }

        FileUtil.writeString(msg, writePath, Charset.defaultCharset());
    }
}
