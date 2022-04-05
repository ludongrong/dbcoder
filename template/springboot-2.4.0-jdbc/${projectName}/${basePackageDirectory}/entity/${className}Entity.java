package ${basepackage}.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ${className}Entity implements Serializable {
    
<#list columns as column>
    @Getter
    @Setter
	private ${column.javaType} ${column.javaNameVariable};
	
	static final public String _${column.name?upper_case} = "${column.name}";
	
</#list>
	public ${className}Entity() {
		super();
	}
}