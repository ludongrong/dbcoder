package ${basePackage}.${projectName}.controller.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
<#list Columns?filter(x -> x.JavaType == "java.sql.Date" || x.JavaType == "java.sql.Timestamp" || x.JavaType == "java.sql.Time") as x>
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
<#break>
</#list>
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
<#-- NULL值的属性不参与序列化 -->
<#-- 等效于如下XML配置 -->
<#-- <property name="serializationInclusion"> -->
<#--     <value type="com.fasterxml.jackson.annotation.JsonInclude.Include">NON_NULL</value> -->
<#-- </property> -->
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "${className}Vo", description = "${Name} 视图交互对象")
public class ${className}Vo implements Serializable {

<#list Columns as column>
<#-- easyexcel：标题属性 -->
	@ExcelProperty("${column.Name}")
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@DateTimeFormat("yyyy-MM-dd HH:mm:ss")
<#elseif column.JavaType == "java.sql.Time">
	@DateTimeFormat("HH:mm:ss")
</#if>
<#-- jackson：序列和反序列的JSON属性名 -->
<#-- ----驼峰： -->
<#-- @JsonProperty(value = "${column.CodeCamelFirstUpper}") -->
<#-- ----下划线-大写： -->
<#-- @JsonProperty(value = "${column.CodeUpper}") -->
	@JsonProperty(value = "${column.CodeUpper}")
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@ApiModelProperty(value = "${column.Name}", name = "${column.CodeUpper}")
	private ${column.JavaType} ${column.CodeCamelFirstLower};

</#list>
	// =================================
	// -- 条件区域
	// =================================

<#list Columns?filter(x -> x.JavaType == "java.lang.String" && x.PrimaryKey != "1") as column>
	@ExcelIgnore
	@JsonProperty(value = "${column.CodeUpper}_LIKE")
	@ApiModelProperty(value = "${column.Name} - 模糊匹配(%xxx%)", name = "${column.CodeUpper}_LIKE")
	private ${column.JavaType} ${column.CodeUnderlineLower}_like;
    
</#list>
<#list Columns?filter(x -> x.JavaType == "java.sql.Date" || x.JavaType == "java.sql.Timestamp" || x.JavaType == "java.sql.Time") as column>
	@ExcelIgnore
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@JsonProperty(value = "${column.CodeUpper}_BEGINTIME")
	@ApiModelProperty(value = "${column.Name} - 起始时间", name = "${column.CodeUpper}_BEGINTIME")
	private ${column.JavaType} ${column.CodeUnderlineLower}_begintime;
    
    @ExcelIgnore
<#if column.JavaType == "java.sql.Date" || column.JavaType == "java.sql.Timestamp">
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
<#elseif column.JavaType == "java.sql.Time">
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
</#if>
	@JsonProperty(value = "${column.CodeUpper}_ENDTIME")
	@ApiModelProperty(value = "${column.Name} - 截止时间", name = "${column.CodeUpper}_ENDTIME")
	private ${column.JavaType} ${column.CodeUnderlineLower}_endtime;
    
</#list>
}