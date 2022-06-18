package io.github.ludongrong.dbcoder.powerdesigner.xml.common;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * PDProject
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-05-09
 */
public class PDProject {

    // 关联ID
    public static final String ELE_ID = "Id";
    // 对象ID
    public static final String ELE_OBJECT_ID = "ObjectID";

    // 别名
    public static final String ELE_OBJECT_NAME = "Name";

    // 命名
    public static final String ELE_OBJECT_CODE = "Code";

    // 命名 - 大写下划线
    public static final String CODE_UNDERLINE_UPPER = "CodeUnderlineUpper";
    // 命名 - 小写下划线
    public static final String CODE_UNDERLINE_LOWER = "CodeUnderlineLower";

    // 命名 - 驼峰(首字符大写)
    public static final String CODE_CAMEL_FIRST_UPPER = "CodeCamelFirstUpper";
    // 命名 - 驼峰(首字符小写)
    public static final String CODE_CAMEL_FIRST_LOWER = "CodeCamelFirstLower";

    // 命名 - 大写
    public static final String CODE_UPPER = "CodeUpper";
    // 命名 - 小写
    public static final String CODE_LOWER = "CodeLower";

    // 短横线 - 大写
    public static final String CODE_KEBAB_CASE_UPPER = "CodeKebabCaseUpper";
    // 短横线 - 小写
    public static final String CODE_KEBAB_CASE_LOWER = "CodeKebabCaseLower";

    // 命名 - 类名 - 驼峰(首字符大写)
    public static final String CODE_CLASS_NAME = "className";
    // 命名 - 类名 - 驼峰(首字符小写)
    public static final String CODE_CLASS_NAME_VARIABLE = "classNameVariable";

    /**
     * 工程模块名
     */
    @Getter
    @Setter
    private String projectName;

    /**
     * 工程包名
     */
    @Getter
    @Setter
    private String basePackage;

    /**
     * 应用类型
     */
    @Getter
    @Setter
    private String appType;

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

    /**
     * 填充项目信息到模型（表、视图、类、接口）
     *
     * @param model
     */
    public void fillInProjectInformation(Map<String, Object> model) {
        // 工程模块名
        model.put("projectName", getProjectName());
        // 包名
        model.put("basePackage", basePackage);
        // 包的文件路径
        model.put("basePackageDirectory", basePackage.replaceAll("\\.", "/"));
        // 应用类型(web, app)
        model.put("appType", getAppType());

        model.put("currentDate", getCurrentDate());
    }

    /**
     * 从请求中获取项目信息
     *
     * @param pdFileBo
     */
    public void readFromSubmitRequest(PdFileBo pdFileBo) {
        setBasePackage(pdFileBo.getBasePackage());
        setProjectName(pdFileBo.getProjectName());
        setAppType(pdFileBo.getAppType());
        setCurrentDate(new Date());
    }

}