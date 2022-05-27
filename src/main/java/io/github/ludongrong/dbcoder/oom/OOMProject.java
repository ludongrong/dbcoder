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
     * 接口
     */
    @Getter
    @Setter
    private List<Map<String, Object>> interfaces;

    /**
     * 类
     */
    @Getter
    @Setter
    private List<Map<String, Object>> classes;

    public static final String OOM_ELE_ID = "Id";

    public static final String OOM_REF1_ID = "Ref1Id";

    public static final String OOM_REF2_ID = "Ref2Id";

    public static final String OOM_REF_EXTENDS = "Extends";

    public static final String OOM_REF_IMPLEMENTS = "Implements";

    public static final String OOM_VISIBILITY = "Visibility";

    public static final String OOM_VOLATILE = "Volatile";

    public static final String OOM_ABSTRACT = "Abstract";

    public static final String OOM_STATIC = "Static";

    public static final String OOM_FINAL = "Final";

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