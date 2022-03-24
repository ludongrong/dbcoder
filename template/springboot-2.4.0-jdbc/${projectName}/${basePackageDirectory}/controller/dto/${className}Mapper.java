package ${basePackage}.controller.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import lombok.Getter;
import lombok.Setter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper {

    ${className}Mapper INSTANCE = Mappers.getMapper(${className}Mapper.class);

    @Mappings({})
    ${className}Bo vo2bo(${className}Vo ${classNameVariable}Vo);
    
    /**
     * 
     * xxx.
     * 
     * <pre>
     * {@code 
     * @Mappings({
     * <#list columns as column>
     * @Mapping(source = "${classNameVariable}Bo.${column.javaName}", target = "${column.javaName}")
     * </#list> 
     * })
     * }</pre>
     *
     * @param pdFileBo {@code PdFileBo}
     * @return {@code PdFileVo}
     */
    @Mappings({})
    ${className}Vo bo2vo(${className}Bo ${classNameVariable}Bo);
    
    List<${className}Vo> bo2vo(List<${className}Bo> ${classNameVariable}BoList);
}
