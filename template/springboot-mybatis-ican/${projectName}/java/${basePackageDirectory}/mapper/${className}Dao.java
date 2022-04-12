package ${basePackage}.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import ${basePackage}.entity.${className};

/**
 * ${tableNameCN} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
 @Mapper
public interface ${className}Dao {
    
    List<${className}> list${className}(${className} ${classNameVariable});
    
}
