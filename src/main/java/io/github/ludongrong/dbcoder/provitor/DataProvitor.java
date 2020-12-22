package io.github.ludongrong.dbcoder.provitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.ludongrong.dbcoder.util.StringUtil;

public class DataProvitor {

    public static Map<String, Object> getTestModel() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("basepackage_dir", "ddddd");
        model.put("projectName", "dFD1");
        model.put("className", "dFD");
        model.put("basepackage", "aaa");
        return model;
    }

    public static Map<String, Object> getModel(Table table) {

        Map<String, Object> model = new HashMap<String, Object>();

        Project project = table.getProject();
        String basepackage = project.getBasePackage() + "." + project.getProjectName();

        model.put("projectName", project.getProjectName());
        model.put("basepackage", basepackage);
        model.put("basepackageDirectory", basepackage.replaceAll("\\.", "/"));
        model.put("tableName", table.getName());
        model.put("className", StringUtil.toJavaClassName(table.getName()));
        model.put("classNameVariable", StringUtil.toJavaVariableName(table.getName()));
        model.put("columns", getModelByColumn(table.getColumns()));
        model.put("primaryColumns", getModelByColumn(table.getColumns().stream().filter(t->{
            return t.isPrimaryKey();
        }).collect(Collectors.toList())));
        model.put("parentReferences", getModelByReference(
                Optional.ofNullable(table.getParentReferences()).orElse(new ArrayList<Reference>())));
        model.put("childReferences", getModelByReference(
                Optional.ofNullable(table.getChildReferences()).orElse(new ArrayList<Reference>())));
        model.put("childSelfReferences", getModelByReference(
                Optional.ofNullable(table.getChildSelfReferences()).orElse(new ArrayList<Reference>())));
        model.put("parentSelfReferences", getModelByReference(
                Optional.ofNullable(table.getParentSelfReferences()).orElse(new ArrayList<Reference>())));

        return model;
    }

    public static List<Map<String, Object>> getModelByColumn(List<Column> columnList) {

        return columnList.stream().collect(Collectors.mapping(t -> {
            String columnType;

            int i = t.getDataType().indexOf("(");
            if (i > 0) {
                columnType = t.getDataType().substring(0, i);
            } else {
                columnType = t.getDataType();
            }

            t.setJavaType(JavaTypesUtils.getPreferredJavaType(t.getTable().getProject().getDbType(), columnType,
                    t.getLength(), t.getPrecision()));
            t.setJavaName(StringUtil.toJavaClassName(t.getName()));

            Map<String, Object> columnModel = new HashMap<String, Object>();
            columnModel.put("javaName", StringUtil.toJavaClassName(t.getName()));
            columnModel.put("javaNameVariable", StringUtil.toJavaVariableName(t.getName()));
            columnModel.put("javaType", t.getJavaType());
            columnModel.put("name", t.getName());
            columnModel.put("dataType", t.getDataType());
            columnModel.put("length", t.getLength());
            columnModel.put("precision", t.getPrecision());
            columnModel.put("mandatory", t.isMandatory());
            columnModel.put("premaryKey", t.isPrimaryKey());
            return columnModel;
        }, Collectors.toList()));
    }

    public static List<Map<String, Object>> getModelByReference(List<Reference> referenceList) {

        return referenceList.stream().collect(Collectors.mapping(t -> {
            Map<String, Object> referenceModel = new HashMap<String, Object>();
            referenceModel.put("className", StringUtil.toJavaClassName(t.getReferenceTable().getName()));
            referenceModel.put("classNameVariable", StringUtil.toJavaVariableName(t.getReferenceTable().getName()));
            referenceModel.put("columns", getModelByColumn(Arrays.asList(t.getJoinColumns())));
            return referenceModel;
        }, Collectors.toList()));
    }
}