package io.github.ludongrong.dbcoder.oom;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Project
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class OOMProject {

    // 别名
    public static final String OOM_OBJECT_NAME = "Name";

    // 命名
    public static final String OOM_OBJECT_CODE = "Code";

    // 大写下划线
    public static final String CODE_UNDERLINE_UPPER = "CodeUnderlineUpper";

    // 小写下划线
    public static final String CODE_UNDERLINE_LOWER = "CodeUnderlineLower";

    // 驼峰 - 首字符大写
    public static final String CODE_CAMEL_FIRST_UPPER = "CodeCamelFirstUpper";

    // 驼峰 - 首字符小写
    public static final String CODE_CAMEL_FIRST_LOWER = "CodeCamelFirstLower";

    /**
     * 工程名
     */
    @Getter
    @Setter
    private String projectName;

    /**
     * 工程包
     */
    @Getter
    @Setter
    private String basePackage;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date currentDate;

    /**
     * 接口、类
     */
    @Getter
    @Setter
    private List<Map<String, Object>> modelList;

    public static final String OOM_NODE_ELE_ID = "Id";

    public static final String OOM_NODE_REF1_ID = "Ref1Id";

    public static final String OOM_NODE_REF2_ID = "Ref2Id";

    public static final String OOM_NODE_OBJECT_ID = "ObjectID";

    public static final String OOM_NODE_PARAMETER_DATA_TYPE = "Parameter.DataType";

    public static final String OOM_OBJECT_DATA_TYPE = "DataType";

    public static final String OOM_OBJECT_RETURN_TYPE = "ReturnType";

    public static final String OOM_OBJECT_EXTENDS = "Extends";

    public static final String OOM_OBJECT_IMPLEMENTS = "Implements";

    public static final String OOM_OBJECT_VISIBILITY = "Visibility";

    public static final String OOM_OBJECT_VOLATILE = "Volatile";

    public static final String OOM_OBJECT_INITIAL_VALUE = "InitialValue";

    public static final String OOM_OBJECT_ABSTRACT = "Abstract";

    public static final String OOM_OBJECT_STATIC = "Static";

    public static final String OOM_OBJECT_FINAL = "Final";

    public static final String OOM_OBJECT_SYNCHRONIZED = "Synchronized";

    public static final String OOM_OBJECT_STRICTFP = "Strictfp";

    public static final String OOM_OBJECT_NATIVE = "Native";

    public enum VISIBILITY_ENUM {

        PRIVATE("-"), PUBLIC("+"), PACKAGE("*"), PROTECTED("#");

        VISIBILITY_ENUM(String value) {
            this.value = value;
        }

        private final String value;

        public String value() {
            return value;
        }

        public static VISIBILITY_ENUM convertEnum(String value, VISIBILITY_ENUM defaultEnum) {
            if (Objects.isNull(value))
                return defaultEnum;

            VISIBILITY_ENUM[] enumList = VISIBILITY_ENUM.values();

            VISIBILITY_ENUM enumVar = CollectionUtil.findOne(Arrays.asList(enumList), enumEle -> {
                return value.equals(enumEle.value());
            });

            return Objects.isNull(enumVar) ? defaultEnum : enumVar;
        }
    }

    public void fillInProjectInformation(Map<String, Object> model) {
        model.put("projectName", getProjectName());
        model.put("basePackage", basePackage);
        model.put("basePackageDirectory", basePackage.replaceAll("\\.", "/"));
        model.put("currentDate", getCurrentDate());
    }

}