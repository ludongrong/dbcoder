package io.github.ludongrong.dbcoder.test;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String path = "F:\\mro\\测验数据\\中兴\\小包";
        List<String> fileNames = FileUtil.listFileNames(path);
        fileNames.forEach((e) -> {
            System.out.println(e);
            FileUtil.writeString("F:\\mro\\测验数据\\test\\down\\" + e,new File(path,FileUtil.mainName(e)+".ctr"), Charset.defaultCharset());
        });
    }
}
