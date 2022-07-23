package ${basePackage}.${projectName}.entity;

import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
<#--
领域模型命名规约
1） 数据对象：xxxDO，xxx 即为数据表名。
2） 数据传输对象：xxxDTO，xxx 为业务领域相关的名称。
3） 展示对象：xxxVO，xxx 一般为网页名称。
4） POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。
-->

/**
 * ${Name} 实体 - 业务
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@NoArgsConstructor
<#-- 有区分表名大小写： Mysql -->
<#-- 不区分表名大小写： Oracle -->
@TableName("${Code}")
public class ${className}Bo extends ${className}Do {

<#if RefParents?exists>
    <#list RefParents as reference>
	@ApiModelProperty(value = "${reference.ParentTable.Name}", name = "${reference.ParentTable.CodeUpper}")
	@Getter
	@Setter
	private ${reference.ParentTable.CodeUpper} ${reference.ParentTable.CodeCamelFirstLower};

    </#list>
</#if>
<#if RefChildren?exists>
    <#list RefChildren as reference>
	@ApiModelProperty(value = "${reference.ChildTable.Name}", name = "${reference.ChildTable.CodeUpper}")
	@Getter
	@Setter
	private List<${reference.ChildTable.CodeUpper}> ${reference.ChildTable.CodeCamelFirstLower}List;

    </#list>
</#if>	
}