package ${basePackage}.${projectName}.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

<#--
领域模型命名规约
1） 数据对象：xxxDO，xxx 即为数据表名。
2） 数据传输对象：xxxDTO，xxx 为业务领域相关的名称。
3） 展示对象：xxxVO，xxx 一般为网页名称。
4） POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。
-->

<#--
@NotEmpty(groups = Save.class)
@NotNull(groups = { Create.class }, message = "Missing longitude")
@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
@Length(min = 2, max = 20)
-->

/**
 * ${Name} 实体
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
<#-- 覆盖默认的 toString() 方法 -->
<#-- 输出格式：ClassName(fieldName=fieleValue, fieldName1=fieleValue) -->
<#-- exclude：排除某些字段，默认为空 -->
@ToString(exclude = {})
@JsonIgnoreProperties(ignoreUnknown = true)
<#-- NULL值的属性不参与序列化 -->
<#-- 等效于如下XML配置 -->
<#-- <property name="serializationInclusion"> -->
<#--     <value type="com.fasterxml.jackson.annotation.JsonInclude.Include">NON_NULL</value> -->
<#-- </property> -->
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "${className}Entity", description = "${Name}")
public class ${className}Entity implements Serializable {

<#list Columns as column>
<#-- 序列和反序列的JSON属性名 -->
<#-- ----驼峰： -->
<#-- @JsonProperty(value = "${column.CodeCamelFirstUpper}") -->
<#-- ----下划线-大写： -->
	@JsonProperty(value = "${column.CodeUnderlineUpper}")
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@ApiModelProperty(value = "${column.Name}", name = "${column.CodeUnderlineUpper}")
    @Getter
    @Setter
	private ${column.JavaType} ${column.CodeCamelFirstLower};

    public static String PK_${column.CodeUnderlineUpper} = "${column.CodeUnderlineUpper}";
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp" || column.JavaType == "java.sql.Time">
    public static String PK_${column.CodeUnderlineUpper}_BEGINTIME = "${column.CodeUnderlineUpper}_BEGINTIME";
    public static String PK_${column.CodeUnderlineUpper}_ENDTIME = "${column.CodeUnderlineUpper}_ENDTIME";
<#elseif column.JavaType == "java.lang.String">
    public static String PK_${column.CodeUnderlineUpper}_LIKE = "${column.CodeUnderlineUpper}_LIKE";
</#if>

</#list>
<#if RefParents?exists>
    <#list RefParents as reference>
	@ApiModelProperty(value = "${reference.ParentTable.Name}", name = "${reference.ParentTable.CodeUnderlineUpper}")
	@Getter
	@Setter
	private ${reference.ParentTable.CodeUnderlineUpper} ${reference.ParentTable.CodeCamelFirstLower};

    </#list>
</#if>
<#if RefChildren?exists>
    <#list RefChildren as reference>
	@ApiModelProperty(value = "${reference.ChildTable.Name}", name = "${reference.ChildTable.CodeUnderlineUpper}")
	@Getter
	@Setter
	private List<${reference.ChildTable.CodeUnderlineUpper}> ${reference.ChildTable.CodeCamelFirstLower}List;

    </#list>
</#if>

	public ${className}Entity() {
		super();
	}
	
}