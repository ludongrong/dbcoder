package ${basepackage}.entity;

import lombok.Getter;
import lombok.Setter;

public class ${className}Bo extends ${className}Do {	
	
<#list parentReferences as reference>
    @Getter
    @Setter
    private ${reference.className}Bo ${reference.classNameVariable}Bo;
    
</#list>
<#list childReferences as reference>
    @Getter
    @Setter
    private List<${reference.className}Bo> ${reference.classNameVariable}BoList;

</#list>
    public ${className}Bo() {
        super();
    }
}