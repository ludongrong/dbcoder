package ${basePackage}.${projectName}.controller.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
<#list Columns?filter(x -> x.JavaType == "java.sql.Date" || x.JavaType == "java.sql.Timestamp" || x.JavaType == "java.sql.Time") as x>
    import com.alibaba.excel.annotation.format.DateTimeFormat;<#t>
    import com.fasterxml.jackson.annotation.JsonFormat;<#t>
    <#break><#t>
</#list>
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ludongrong.common.controller.domain.BaseVo;
<#if HasPrimaryKey == '1'>
    import io.github.ludongrong.common.validation.RemoveValidGroup;<#t>
    import io.github.ludongrong.common.validation.UpdatePostValidGroup;<#t>
</#if>
import ${basePackage}.${projectName}.entity.${className}Bo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

<#--
领域模型命名规约
1） 数据对象：xxxDO，xxx 即为数据表名。
2） 数据传输对象：xxxDTO，xxx 为业务领域相关的名称。
3） 展示对象：xxxVO，xxx 一般为网页名称。
4） POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。

校验
@NotEmpty(groups = Save.class)
@NotNull(groups = { Create.class }, message = "Missing longitude")
@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
@Length(min = 2, max = 20)
-->
/**
 * ${Name} 视图实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
<#-- 覆盖默认的 toString() 方法 -->
<#-- 输出格式：ClassName(fieldName=fieleValue, fieldName1=fieleValue) -->
<#-- exclude：排除某些字段，默认为空 -->
@ToString(exclude = {})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
<#-- NULL值的属性不参与序列化 -->
<#-- 等效于如下XML配置 -->
<#-- <property name="serializationInclusion"> -->
<#--     <value type="com.fasterxml.jackson.annotation.JsonInclude.Include">NON_NULL</value> -->
<#-- </property> -->
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "${CodeUpper}_VO", description = "${Name}-视图交互对象")
public class ${className}Vo extends BaseVo implements Serializable {

    // =================================
    // -- VO field; 内部命名格式：驼峰(第一个字符小写)，输出命名格式：大写下划线
    // =================================

<#list Columns as column>
    public static final String PK_${column.CodeUpper} = "${column.CodeUpper}";

<#if column.PrimaryKey == "1">
    @NotEmpty(groups = {UpdatePostValidGroup.class, RemoveValidGroup.class}, message = "Missing " + PK_${column.CodeUpper})
</#if>
    <#-- easyexcel：标题属性 --><#t>
    @ExcelProperty("${column.Name}")
    <#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp"><#t>
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    <#elseif column.JavaType == "java.sql.Time"><#t>
    @DateTimeFormat("HH:mm:ss")
    </#if><#t>
<#t>
<#t>
<#t>
    <#-- jackson：序列和反序列的JSON属性名 --><#t>
    @JsonProperty(value = PK_${column.CodeUpper})
    <#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp"><#t>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    <#elseif column.JavaType == "java.sql.Time"><#t>
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    </#if><#t>
    @ApiModelProperty(value = "${column.Name}", name = PK_${column.CodeUpper})
    @Getter
    private ${column.JavaType} ${column.CodeCamelFirstLower};
    
    public void set${column.CODE_CAMEL_FIRST_UPPER}(${column.JavaType} ${column.CodeCamelFirstLower}) {
        attrs.put(PK_${column.CodeUpper}, ${column.CodeCamelFirstLower});
        this.${column.CodeCamelFirstLower} = ${column.CodeCamelFirstLower};
    }

</#list>
    // =================================
    // -- 条件区域，命名格式：小写下划线
    // =================================

<#t>
<#t>
<#t>
<#-- 字符串类型 -->
<#list Columns?filter(x -> x.JavaType == "java.lang.String" && x.PrimaryKey != "1") as column>
    private static final String CK_${column.CodeUpper}_LIKE = "${column.CodeUpper}_LIKE";

    @ExcelIgnore
    @JsonProperty(value = CK_${column.CodeUpper}_LIKE)
    @ApiModelProperty(value = "${column.Name} - 模糊匹配(%xxx%)", name = CK_${column.CodeUpper}_LIKE)
    private ${column.JavaType} ${column.CodeUnderlineLower}_like;

</#list>
<#t>
<#t>
<#t>
<#-- 时间类型 -->
<#list Columns?filter(x -> x.JavaType == "java.sql.Date" || x.JavaType == "java.sql.Timestamp" || x.JavaType == "java.sql.Time") as column>
    private static final String CK_${column.CodeUpper}_BEGIN = "${column.CodeUpper}_BEGIN";

    @ExcelIgnore
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
    @JsonProperty(value = CK_${column.CodeUpper}_BEGIN)
    @ApiModelProperty(value = "${column.Name} - 起始时间", name = CK_${column.CodeUpper}_BEGIN)
    private ${column.JavaType} ${column.CodeUnderlineLower}_begin;

    private static final String CK_${column.CodeUpper}_END = "${column.CodeUpper}_END";

    @ExcelIgnore
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
    @JsonProperty(value = CK_${column.CodeUpper}_END)
    @ApiModelProperty(value = "${column.Name} - 截止时间", name = CK_${column.CodeUpper}_END)
    private ${column.JavaType} ${column.CodeUnderlineLower}_end;

</#list>

    // =================================
    // -- VO field 映射 Do field
    // =================================

    static {
    <#list Columns as column>
        _VO_FIELD_2_DO_FIELD.put(PK_${column.CodeUpper}, ${className}Bo.PK_${column.CodeUpper});
    </#list>
    }
}