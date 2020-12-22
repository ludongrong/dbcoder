package io.github.ludongrong.dbcoder.provitor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class ReferenceKey {

    @Getter
    @Setter
    private String parentTableKeyId;

    @Getter
    @Setter
    private String childTableKeyId;

    @Getter
    @Setter
    private List<String[]> join;

    public List<Column> mappingColumn(Map<String, Column> primaryMap, Map<String, Column> childMap) {
        return getJoin().stream().collect(Collectors.mapping((joins) -> {
            for (String join : joins) {
                if (primaryMap.get(join) == null) {
                    return childMap.get(join);
                }
            }
            return null;
        }, Collectors.toList())).stream().filter(c -> {
            return c != null;
        }).collect(Collectors.toList());
    }
}