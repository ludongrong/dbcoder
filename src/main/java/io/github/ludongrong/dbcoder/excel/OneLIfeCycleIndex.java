package io.github.ludongrong.dbcoder.excel;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class OneLIfeCycleIndex implements TemplateMethodModelEx {

    int currentIndex;

    int startIndex;

    public OneLIfeCycleIndex(int startIndex) {
        this.startIndex = startIndex;
        this.currentIndex = startIndex;
    }

    public void clear() {
        clear(this.startIndex);
    }

    public void clear(int startIndex) {
        this.currentIndex = startIndex;
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() > 0) {
            if (arguments.get(0) instanceof SimpleNumber) {
                int startAgainNumber = ((SimpleNumber) arguments.get(0)).getAsNumber().intValue();
                clear(startAgainNumber);
            }
        }
        return new SimpleNumber(currentIndex++);
    }

}
