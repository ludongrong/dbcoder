package ${basepackage}.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import ${basepackage}.entity.${className};
import ${basepackage}.controller.vo.${className}Vo;

public class ${className}Mapper {

    /**
     * 
     * vo 转 entity.
     *
     * @param ${classNameVariable}Vo
     * @return ${className}
     */
    public static ${className} voToEntity(${className}Vo ${classNameVariable}Vo) {
        
    	if (${classNameVariable}Vo == null) {
            return null;
        }

        ${className} ${classNameVariable} = new ${className}();

        <#list columns as column>
		${classNameVariable}.set${column.javaName}(${classNameVariable}Vo.get${column.javaName}());
		</#list>

        return ${classNameVariable};
    }
    
    /**
     * 
     * vo 转 entity.
     * 
     * @param ${classNameVariable}Vos
     * @return List<${className}>
     */
    public static List<${className}> voToEntity(List<${className}Vo> ${classNameVariable}Vos) {
        
    	if (${classNameVariable}Vos == null) {
            return null;
        }

        List<${className}> list = new ArrayList<>(${classNameVariable}Vos.size());
        for (${className}Vo ${classNameVariable}Vo : ${classNameVariable}Vos) {
            list.add(voToEntity(${classNameVariable}Vo));
        }

        return list;
    }
    
    /**
     * 
     * entity 转 vo.
     * 
     * @param ${classNameVariable}
     * @return ${className}Vo
     */
    public static ${className}Vo entityToVo(${className} ${classNameVariable}) {
        
    	if (${classNameVariable} == null) {
            return null;
        }

    	${className}Vo ${classNameVariable}Vo = new ${className}Vo();

    	<#list columns as column>
    	${classNameVariable}Vo.set${column.javaName}(${classNameVariable}.get${column.javaName}());
		</#list>

        return ${classNameVariable}Vo;
    }
    
    /**
     * 
     * entity 转 vo.
     * 
     * @param ${classNameVariable}s
     * @return List<${className}Vo>
     */
    public static List<${className}Vo> entityToVo(List<${className}> ${classNameVariable}s) {
        
    	if (${classNameVariable}s == null) {
            return null;
        }

        List<${className}Vo> list = new ArrayList<>(${classNameVariable}s.size());
        for (${className} ${classNameVariable} : ${classNameVariable}s) {
            list.add(entityToVo(${classNameVariable}));
        }

        return list;
    }
    
}

