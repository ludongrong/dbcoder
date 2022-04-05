package ${basepackage}.model;

import lombok.Getter;
import lombok.Setter;

public class ${className}Model extends ${className}Entity {	
	
<#list parentReferences as reference>
    @Getter
    @Setter
    private ${reference.className}Model ${reference.classNameVariable}Model;
    
</#list>
<#list childReferences as reference>
    @Getter
    @Setter
    private List<${reference.className}Model> ${reference.classNameVariable}ModelList;

</#list>
    public ${className}Model() {
        super();
    }
}