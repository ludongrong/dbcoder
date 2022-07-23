package ${basePackage}.${projectName}.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
<#--
领域模型命名规约
1） 数据对象：xxxDO，xxx 即为数据表名。
2） 数据传输对象：xxxDTO，xxx 为业务领域相关的名称。
3） 展示对象：xxxVO，xxx 一般为网页名称。
4） POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。
-->

/**
 * ${Name} 实体 - 数据库映射
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Data
@NoArgsConstructor
public class ${className}Do implements Serializable {

<#list Columns as column>
	@TableField(value = "${column.Code}", jdbcType = JdbcType.${column.JdbcType})
	private ${column.JavaType} ${column.CodeCamelFirstLower};

    public static final String PK_${column.CodeUpper} = "${column.CodeUpper}";

</#list>
}