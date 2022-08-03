package ${basePackage}.${projectName}.controller.domain;

import ${basePackage}.${projectName}.entity.TestBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ${Name} 实体字段映射
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper {

    ${className}Mapper INSTANCE = Mappers.getMapper(${className}Mapper.class);
    
    // =================================
    // -- Vo -> Bo
    // =================================

    /**
     * 
     * Vo -> Bo 视图对象转业务对象.
     * 
     * <pre>
     * {@code 
     *    @Mappings({
     *    <#list Columns as column><#t>
     *        @Mapping(source = "${classNameVariable}Vo.${column.CodeCamelFirstLower}", target = "${column.CodeCamelFirstLower}")
     *    </#list><#t>
     *    })
     * }
     * </pre>
     *
     * @param ${classNameVariable}Vo {@code ${className}Vo}
     * @return {@code ${className}Bo}
     */
    @Mappings({})
    ${className}Bo toBo(${className}Vo ${classNameVariable}Vo);

    List<${className}Bo> toBo(List<${className}Vo> ${classNameVariable}VoList);
    
    // =================================
    // -- Bo -> Vo
    // =================================

    /**
     * 
     * Bo -> Vo 业务对象转视图对象.
     * 
     * <pre>
     * {@code 
     *    @Mappings({
     *    <#list Columns as column><#t>
     *        @Mapping(source = "${classNameVariable}Bo.${column.CodeCamelFirstLower}", target = "${column.CodeCamelFirstLower}")
     *    </#list><#t>
     *    })
     * }
     * </pre>
     *
     * @param ${classNameVariable}Bo {@code ${className}Bo}
     * @return {@code ${className}Vo}
     */
    @Mappings({})
    ${className}Vo toVo(${className}Bo ${classNameVariable}Bo);

    List<${className}Vo> toVo(List<${className}Bo> ${classNameVariable}BoList);
}
