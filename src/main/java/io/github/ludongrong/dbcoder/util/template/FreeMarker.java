package io.github.ludongrong.dbcoder.util.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cn.hutool.core.lang.UUID;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NonNull;

public final class FreeMarker {

    static final private Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

    static {
        configuration.setOutputEncoding("UTF-8");
        configuration.setLocale(Locale.CHINA);
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        configuration.setObjectWrapper(new SimpleObjectWrapper(Configuration.VERSION_2_3_22));
        configuration.setNumberFormat("0.######");
        configuration.setBooleanFormat("true,false");
        configuration.setTimeFormat("HH:mm:ss");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static void process(String name, @NonNull String templateString, @NonNull Map<String, Object> model, @NonNull Writer out, String include) throws IOException, TemplateException {
        Template template = new Template(name, templateString, configuration);
        Environment env = template.createProcessingEnvironment(model, out, null);
        env.include(new Template(UUID.randomUUID().toString(), include, configuration));
        env.process();
    }

    public static String process(String name, @NonNull String templateString, @NonNull Map<String, Object> model) throws IOException, TemplateException {
        StringWriter out = new StringWriter();
        new Template(name, templateString, configuration).process(model, out);
        return out.toString();
    }

}