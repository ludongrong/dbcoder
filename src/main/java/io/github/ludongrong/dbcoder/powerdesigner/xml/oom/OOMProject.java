package io.github.ludongrong.dbcoder.powerdesigner.xml.oom;

import cn.hutool.core.collection.CollectionUtil;
import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler.ClassHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler.GeneralizationHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler.InterfaceHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.oom.handler.RealizationHandler;
import io.github.ludongrong.dbcoder.powerdesigner.xml.common.PDProject;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * OOMProject
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class OOMProject extends PDProject {

    @Getter
    @Setter
    private InterfaceHandler interfaceHandler;

    @Getter
    @Setter
    private ClassHandler classHandler;

    @Getter
    @Setter
    private GeneralizationHandler generalizationHandler;

    @Getter
    @Setter
    private RealizationHandler realizationHandler;

    public static final String OOM_NODE_REF1_ID = "Ref1Id";

    public static final String OOM_NODE_REF2_ID = "Ref2Id";

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

    public static OOMProject onBeforeRead(PdFileBo pdFileBo) {
        OOMProject project = new OOMProject();
        project.readFromSubmitRequest(pdFileBo);
        return project;
    }

}