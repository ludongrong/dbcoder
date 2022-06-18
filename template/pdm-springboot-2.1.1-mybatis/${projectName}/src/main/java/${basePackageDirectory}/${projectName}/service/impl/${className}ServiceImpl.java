package ${basePackage}.${projectName}.service.impl;

import com.alibaba.excel.EasyExcel;

import ${basePackage}.${projectName}.service.DownloadService;
import ${basePackage}.${projectName}.dao.${className}Mapper;
import ${basePackage}.${projectName}.entity.${className}Entity;
import ${basePackage}.${projectName}.service.${className}Service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * ${Name} 业务逻辑实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Service("${classNameVariable}Service")
public class ${className}ServiceImpl implements ${className}Service {
    
	@Resource
    private ${className}Mapper ${classNameVariable}Mapper;

<#--
    @Autowired
    DownConfDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }
-->
	
	@Resource
    private DownloadService downloadService;
	
    @Transactional
    @Override
    public ${className}Entity save(${className}Entity ${classNameVariable}) {
        ${classNameVariable}Mapper.insert(${classNameVariable});
    }
    
    @Transactional
    @Override
    public void remove(${className}Entity ${classNameVariable}) {
        ${classNameVariable}Mapper.delete(${classNameVariable});
    }
    
    @Transactional
    @Override
    public void update(${className}Entity ${classNameVariable}) {
        ${classNameVariable}Mapper.update(${classNameVariable});
    }
    
    <#if HasPrimaryKey == '1'>
    @Override
    public ${className}Entity getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>) {
        return ${classNameVariable}Mapper.getByPrimary(<#list Columns as column><#if column.PrimaryKey == "1">${column.JavaType} ${column.CodeCamelFirstLower}</#if><#if column_has_next>, </#if></#list>);
    }
    </#if>
	
	@Override
    public PageInfo<${className}Entity> paging(Map<String, Object> paramMap) {
        Page.startPage(paramMap);
        return new PageInfo<>(${classNameVariable}Mapper.list(paramMap));
    }
    
    @Override
    public RestResult<VuePage<${className}>> list(${className} ${classNameVariable}) {
        VuePage<${className}> result = null;
        
        try {
            List<${className}> dataList = ${classNameVariable}Mapper.list${className}(${classNameVariable});
            result = new VuePage<${className}>(dataList);
        } catch (Exception e) {
            log.error("${className}ServiceImpl", e);
            return RestResult.buildErrorResult("查询 “${Name}” 失败");
        }
        return RestResult.buildSuccessResult(result);
    }
	
    @Override
    public List<${className}Entity> list(Map<String, Object> paramMap) {
        return ${classNameVariable}Mapper.list(paramMap);
    }
	
	@Async
    @Override
    public String listForExport(String fileName, Map<String, Object> paramMap) {
        File reallyFile = downloadService.reallyExcel(fileName);

        boolean writeFlag = false;
        try {
            List<${className}Entity> ${classNameVariable}List = ${classNameVariable}Mapper.queryList(paramMap);
            EasyExcel.write(reallyFile, ${className}Entity.class).sheet(fileName).doWrite(${classNameVariable}List);
            writeFlag = true;
        } catch (Exception e) {
            writeFlag = false;
            log.error("生成{}失败，错误信息：{}", fileName, e.getMessage(), e);
        }

        return downloadService.ok(reallyFile, writeFlag);
    }

<#if RefParents?exists>
    <#list RefParents as reference>
    public VuePage<${className}> listBy${reference.ParentTable.className}${reference_index}(Map<String, Object> paramMap) {
        List<${className}> dataList = ${classNameVariable}Mapper.listBy${reference.ParentTable.className}${reference_index}(paramMap);
        return new VuePage<${className}>(dataList);
    }
    
    </#list>
    <#list RefParents as reference>
    public VuePage<${className}> listRefBy${reference.ParentTable.className}${reference_index}(Map<String, Object> paramMap) {
        List<${className}> dataList = ${classNameVariable}Mapper.listRefBy${reference.ParentTable.className}${reference_index}(paramMap);
        return new VuePage<${className}>(dataList);
    }
    
    </#list>
</#if>
<#if RefChildren?exists>
    <#list RefChildren as reference>
    public VuePage<${className}> listRefBy${reference.ChildTable.className}Many${reference_index}(Map<String, Object> paramMap) {
        List<${className}> dataList = ${classNameVariable}Mapper.listRefBy${reference.ChildTable.className}Many${reference_index}(paramMap);
        return new VuePage<${className}>(dataList);
    }
    
    </#list>
</#if>
}
