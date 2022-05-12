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
    
    void insert(${className} ${classNameVariable});
    
    void update(${className} ${classNameVariable});
    
    void delete(${className} ${classNameVariable});
    
    List<${className}> queryListByAdmin(${className} ${classNameVariable});

    int queryCountByAdmin(${className} ${classNameVariable});
    
    List<${className}> queryListByUser(${className} ${classNameVariable});

    int queryCountByUser(${className} ${classNameVariable});
}
