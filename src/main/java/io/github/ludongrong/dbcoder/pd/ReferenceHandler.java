package io.github.ludongrong.dbcoder.pd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.provitor.ReferenceKey;
import lombok.Getter;

public class ReferenceHandler implements ElementHandler {

    @Getter
    List<ReferenceKey> referenceList = new ArrayList<ReferenceKey>();

    public void onStart(ElementPath path) {
    }

    public void onEnd(ElementPath path) {
        Element element = path.getCurrent();

        String parentId = Optional.ofNullable(element.element("ParentTable")).map(t -> {
            return t.element("Table");
        }).map(t -> {
            return t.attributeValue("Ref");
        }).orElse(null);

        String childId = Optional.ofNullable(element.element("ChildTable")).map(t -> {
            return t.element("Table");
        }).map(t -> {
            return t.attributeValue("Ref");
        }).orElse(null);

        List<String[]> joinList = Optional.ofNullable(element.element("Joins")).map(t -> {
            return t.elements("ReferenceJoin");
        }).map(t -> {
            List<String[]> joinResult = new ArrayList<String[]>();
            for (Element e : t) {
                String[] k = new String[2];
                k[0] = DomUtil.getReferenceJoinKey(e, "Object1");
                k[1] = DomUtil.getReferenceJoinKey(e, "Object2");
                if (k[0] == null) {
                    continue;
                }
                if (k[1] == null) {
                    continue;
                }
                joinResult.add(k);
            }
            return joinResult;
        }).orElse(null);

        path.getCurrent().detach();

        if (parentId == null) {
            return;
        }

        if (childId == null) {
            return;
        }

        if (joinList.isEmpty()) {
            return;
        }

        ReferenceKey referenceKey = new ReferenceKey();
        referenceKey.setParentTableKeyId(parentId);
        referenceKey.setChildTableKeyId(childId);
        referenceKey.setJoin(joinList);
        referenceList.add(referenceKey);
    }
}
