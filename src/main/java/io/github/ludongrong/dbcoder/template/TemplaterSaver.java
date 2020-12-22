package io.github.ludongrong.dbcoder.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.UUID;

import cn.hutool.core.util.CharsetUtil;

public class TemplaterSaver {

    private File generateDirectoryFile;

    public TemplaterSaver(String generateDirectory) {
        super();
        generateDirectoryFile = new File(generateDirectory, UUID.randomUUID().toString());
        if (generateDirectoryFile.exists() == false) {
            generateDirectoryFile.mkdirs();
        }
    }

    public void writeDirectory(String local) {
        File newFile = new File(generateDirectoryFile, local);
        if (newFile.exists() == false) {
            newFile.mkdirs();
        }
    }

    public Writer writeFile(String local) throws UnsupportedEncodingException, FileNotFoundException {
        File newFile = new File(generateDirectoryFile, local);
        if (newFile.getParentFile().exists() == false) {
            newFile.getParentFile().mkdirs();
        }

        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), CharsetUtil.UTF_8));
    }
}