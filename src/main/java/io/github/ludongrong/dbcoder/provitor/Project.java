package io.github.ludongrong.dbcoder.provitor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class Project {

    @Getter
    @Setter
    private List<Table> tables;

    @Getter
    @Setter
    private String basePackage;

    @Getter
    @Setter
    private String projectName;

    @Getter
    @Setter
    private String dbType;

    public Map<String, Table> toTableMap() {
        return getTables().stream().collect(Collectors.toMap((val) -> {
            return val.getId();
        }, (val) -> {
            return val;
        }, (oldValue, newValue) -> newValue));
    }
}