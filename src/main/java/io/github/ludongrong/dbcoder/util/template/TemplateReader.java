package io.github.ludongrong.dbcoder.util.template;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;
import lombok.Getter;

public class TemplateReader {


    static public class TemplateFilter implements FileFilter {

        @Getter
        private String macro;

        @Override
        public boolean accept(File pathname) {
            if (pathname.getName().equals("macro.include")) {
                File macroFile = pathname;
                if (macroFile.exists()) {
                    macro = FileUtil.readString(macroFile, CharsetUtil.UTF_8);
                }
                return false;
            } else {
                return true;
            }
        }
    }

    // 模板目录
    private File templateDirectoryFile;

    // 存储已经处理过的文件路径 --- 不是模板文件路径
    // 不重复处理
    private HashSet<String> localPathTagMap = new HashSet<>();

    private ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    private ZipOutputStream zipOutput;

    public TemplateReader(String templateDirectory) throws Exception {

        if (templateDirectory == null) {
            throw new Exception("");
        }

        templateDirectoryFile = new File(templateDirectory);
        if (templateDirectoryFile.exists() == false) {
            throw new Exception("");
        }

        this.zipOutput = new ZipOutputStream(byteOutput);
    }

    /**
     * 处理模板
     *
     * @param model
     * @throws Exception
     */
    public void generate(Map<String, Object> model) throws Exception {

        TemplateFilter templateFilter = new TemplateFilter();

        /*文件放入栈*/
        Stack<File> storeFileStack = new Stack<>();
        storeFileStack.addAll(Arrays.asList(templateDirectoryFile.listFiles(templateFilter)));

        /*模板目录，包含macro，用于标识是目标目录*/
        String macro = templateFilter.getMacro();
        if (StrUtil.isBlank(macro)) {
            throw new IllegalStateException("Missing macro");
        }

        /*执行到栈里面没有文件*/
        while (storeFileStack.empty() == false) {
            File templateFile = storeFileStack.pop();

            String subPath = FileUtil.subPath(templateDirectoryFile.getPath(), templateFile);
            String newPath = FreeMarker.process("path", subPath, model);

            if (templateFile.isDirectory()) { // 创建ZIP目录后，继续下钻，把下级文件再放入栈
                writeDirectoryToZip(newPath);
                storeFileStack.addAll(Arrays.asList(templateFile.listFiles()));
            } else {
                processTemplate(templateFile.getName(), newPath, model, templateFile, macro);
            }
        }
    }

    /**
     * 处理模板
     *
     * @param name
     * @param local
     * @param model
     * @param templateFile
     * @param macro
     * @throws IORuntimeException
     * @throws IOException
     * @throws TemplateException
     */
    private void processTemplate(String name, String local, Map<String, Object> model, File templateFile, String macro) throws IORuntimeException, IOException, TemplateException {

        if (!localPathTagMap.add(local))
            return;

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Writer out = new BufferedWriter(new OutputStreamWriter(outStream, CharsetUtil.UTF_8));

        try {
            String template = FileUtil.readString(templateFile, CharsetUtil.CHARSET_UTF_8);
            FreeMarker.process(name,
                    template,
                    model,
                    out,
                    macro);

            writeFileToZip(local, outStream);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 读取模板结束
     *
     * @return
     * @throws Exception
     */
    public ByteArrayInputStream finish() throws Exception {
        finishToZip();
        return new ByteArrayInputStream(byteOutput.toByteArray());
    }

    /**
     * ZIP -> 关闭
     *
     * @throws IOException
     */
    private void finishToZip() throws IOException {
        zipOutput.flush();
        zipOutput.finish();
    }

    /**
     * ZIP -> 创建目录
     *
     * @param local
     * @throws IOException
     */
    private void writeDirectoryToZip(String local) throws IOException {

        String path = local + "/";

        if (!localPathTagMap.add(path))
            return;

        ZipEntry ze = new ZipEntry(path);
        zipOutput.putNextEntry(ze);
        zipOutput.closeEntry();
    }

    /**
     * ZIP -> 创建文件
     *
     * @param local
     * @throws IOException
     */
    private void writeFileToZip(String local, ByteArrayOutputStream outStream) throws IOException {
        byte[] data = outStream.toByteArray();
        ZipEntry ze = new ZipEntry(local);
        zipOutput.putNextEntry(ze);
        zipOutput.write(data, 0, data.length);
        zipOutput.closeEntry();
    }

}