package io.github.ludongrong.dbcoder.template;

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
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;
import io.github.ludongrong.dbcoder.provitor.Project;
import io.github.ludongrong.dbcoder.provitor.Table;
import lombok.Getter;

public class TemplateReader {

    private File templateDirectoryFile;

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
    };

    private HashSet<String> names = new HashSet<>();

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private ZipOutputStream zipOutputStream;

    public TemplateReader(String templateDirectory) throws Exception {

        if (templateDirectory == null) {
            throw new Exception("");
        }

        templateDirectoryFile = new File(templateDirectory);
        if (templateDirectoryFile.exists() == false) {
            throw new Exception("");
        }

        this.zipOutputStream = new ZipOutputStream(baos);
    }

    public ByteArrayInputStream generate(Project project) throws Exception {

        Iterator<Table> iterable = project.getTables().iterator();
        while (iterable.hasNext()) {
            generate(Table.toModel(iterable.next()));
        }

        zipOutputStream.flush();
        zipOutputStream.finish();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void generate(Map<String, Object> model) throws Exception {

        TemplateFilter templateFilter = new TemplateFilter();

        Stack<File> st = new Stack<File>();
        st.addAll(Arrays.asList(templateDirectoryFile.listFiles(templateFilter)));

        String macro = templateFilter.getMacro();
        if (StrUtil.isBlank(macro)) {
            throw new IllegalStateException("Missing macro");
        }
        
        while (st.empty() == false) {
            File templateFile = st.pop();

            String subPath = FileUtil.subPath(templateDirectoryFile.getPath(), templateFile);
            String newPath = FreeMarker.process(subPath, model);

            if (templateFile.isDirectory()) {
                writeDirectory(newPath);
                st.addAll(Arrays.asList(templateFile.listFiles()));
            } else {
                processTemplate(newPath, model, templateFile, macro);
            }
        }
    }

    private void writeDirectory(String local) throws IOException {

        String path = local + "/";

        if (! names.add(path)) {
            return;
        }

        ZipEntry ze = new ZipEntry(path);
        zipOutputStream.putNextEntry(ze);
        zipOutputStream.closeEntry();
    }

    private void processTemplate(String local, Map<String, Object> model, File templateFile, String macro)
            throws IORuntimeException, IOException, TemplateException {

        if (! names.add(local)) {
            return;
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        Writer out = new BufferedWriter(new OutputStreamWriter(outStream, CharsetUtil.UTF_8));

        try {
            FreeMarker.process(FileUtil.readString(templateFile, CharsetUtil.CHARSET_UTF_8), model, out,
                    Optional.ofNullable(macro).orElse(""));

            byte[] data = outStream.toByteArray();
            ZipEntry ze = new ZipEntry(local);
            zipOutputStream.putNextEntry(ze);
            zipOutputStream.write(data, 0, data.length);
            zipOutputStream.closeEntry();
        } finally {
            IoUtil.close(out);
        }
    }
}