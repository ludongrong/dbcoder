<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.model.${className};

/**
 * 
 * ${className} DAO 数据层
 * 
 * @author 卢冬榕
 * @email 736779458@qq.com
 *
 */
public interface I${className}Dao
{
	int create(${className} ${classNameLower});
}
