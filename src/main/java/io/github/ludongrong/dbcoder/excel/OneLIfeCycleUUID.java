package io.github.ludongrong.dbcoder.excel;

import cn.hutool.core.util.IdUtil;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneLIfeCycleUUID implements TemplateMethodModelEx {

    Map<String, Object> cache = new HashMap<>(1000);

    public String randomUUID7() {

        String key;
        do {
            key = IdUtil.randomUUID().substring(0, 7);
        } while (cache.containsKey(key));

        cache.put(key, null);

        return key;
    }

    public void clear() {
        cache.clear();
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        switch (checkInputNumber(arguments)) {
            case 36:
                return IdUtil.randomUUID();
            default:
                return randomUUID7();
        }
    }

    private int checkInputNumber(List arguments) {
        if (arguments.size() > 0) {
            if (arguments.get(0) instanceof SimpleNumber) {
                int startAgainNumber = ((SimpleNumber) arguments.get(0)).getAsNumber().intValue();
                return startAgainNumber;
            }
        }
        return 0;
    }

}
