package ${basePackage}.entity.header;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * ${tableNameCN} 实体字段说明
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@ApiModel(value = "${className}Header", description = "${tableNameCN}")
public class ${className}Header implements Serializable {

<#list columns as column>
	@JsonProperty(value = "${column.name?upper_case}")
	private final String ${column.javaNameVariable} = "${column.nameCN}";
	
</#list>	
    private static final ${className}Header ${tableName?upper_case} = new ${className}Header();

    public ${className}Header() {
		super();
	}

    public static ${className}Header getInstance() {
        return ${tableName?upper_case};
    }
}
