package io.github.ludongrong.dbcoder.provitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class Table extends Element {

    private static final long serialVersionUID = 5683537028079665552L;

    @Getter
    @Setter
    private List<Column> columns;

    @Getter
    @Setter
    private List<Reference> parentReferences;

    @Getter
    @Setter
    private List<Reference> parentSelfReferences;

    @Getter
    @Setter
    private List<Reference> childReferences;

    @Getter
    @Setter
    private List<Reference> childSelfReferences;

    @Getter
    @Setter
    private Project project;

    public void addChildReference(Reference reference) {
        childReferences = Optional.ofNullable(childReferences).orElse(new ArrayList<Reference>());
        childReferences.add(reference);
    }

    public void addParentReference(Reference reference) {
        parentReferences = Optional.ofNullable(parentReferences).orElse(new ArrayList<Reference>());
        parentReferences.add(reference);
    }

    public void addChildSelfReference(Reference reference) {
        childSelfReferences = Optional.ofNullable(childSelfReferences).orElse(new ArrayList<Reference>());
        childSelfReferences.add(reference);
    }

    public void addParentSelfReference(Reference reference) {
        parentSelfReferences = Optional.ofNullable(parentSelfReferences).orElse(new ArrayList<Reference>());
        parentSelfReferences.add(reference);
    }

    public Map<String, Column> toPrimaryMap() {
        return getColumns().stream().filter(t1 -> {
            return t1.isPrimaryKey();
        }).collect(Collectors.toMap(val1 -> {
            return val1.getId();
        }, val2 -> {
            return val2;
        }, (oldValue, newValue) -> newValue));
    }

    public Map<String, Column> toColumnMap() {
        return getColumns().stream().collect(Collectors.toMap(val1 -> {
            return val1.getId();
        }, val2 -> {
            return val2;
        }, (oldValue, newValue) -> newValue));
    }
}