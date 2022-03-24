package ${basePackage}.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ${className}Do implements Serializable {
    
<#list columns as column>
    @Getter
    @Setter
	private ${column.javaType} ${column.javaNameVariable};
	
	static final public String _${column.name?upper_case} = "${column.name}";
	
</#list>
	public ${className}Do() {
		super();
	}
}