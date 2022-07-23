package io.github.ludongrong.dbcoder.util.template;

import io.github.ludongrong.dbcoder.common.exception.BadGatewayException;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TemplateUtil {

    public static ByteArrayInputStream generateTemplate(String templatePath, List<Map<String, Object>> modelList) {
        try {
            TemplateReader templateReader = new TemplateReader(templatePath);

            Iterator<Map<String, Object>> iterable = modelList.iterator();
            while (iterable.hasNext()) {
                templateReader.generate(iterable.next());
            }

            return templateReader.finish();
        } catch (Exception e) {
            throw new BadGatewayException(String.format("Can not generate template. --- %s", UUID.randomUUID().toString()), e);
        }
    }

    public static ByteArrayInputStream generateTemplate(String templatePath, Map<String, Object> model) {
        try {
            TemplateReader templateReader = new TemplateReader(templatePath);
            templateReader.generate(model);
            return templateReader.finish();
        } catch (Exception e) {
            throw new BadGatewayException(String.format("Can not generate template. --- %s", UUID.randomUUID().toString()), e);
        }
    }

}