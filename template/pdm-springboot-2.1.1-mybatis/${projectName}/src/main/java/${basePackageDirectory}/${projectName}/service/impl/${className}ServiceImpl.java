package ${basePackage}.${projectName}.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.ludongrong.common.service.DownloadService;
import ${basePackage}.${projectName}.entity.${className}Bo;
import ${basePackage}.${projectName}.mapper.${className}Mapper;
import ${basePackage}.${projectName}.service.I${className}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@DS("primary")
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}Bo> implements I${className}Service {
    
    private final ${className}Mapper ${classNameVariable}Mapper;

    @Autowired
    ${className}ServiceImpl(${className}Mapper ${classNameVariable}Mapper) {
        super();
        this.${classNameVariable}Mapper = ${classNameVariable}Mapper;
    }
	
	@Resource
    private DownloadService downloadService;

	@Autowired
    public void setDownloadService(@Qualifier("downloadService") DownloadService downloadService) {
        this.downloadService = downloadService;
    }
    
<#if HasPrimaryKey == '1'>
    @Override
    public ${className}Bo getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.JavaType} ${column.CodeCamelFirstLower}<#sep>, </#sep></#list>) {
        return ${classNameVariable}Mapper.getByPrimary(<#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.CodeCamelFirstLower}<#sep>, </#sep></#list>);
    }

</#if>
	@Override
    public List<${className}Bo> list(Map<String, Object> paramMap) {
        return ${classNameVariable}Mapper.list(paramMap);
    }

	@Async
    @Override
    public String listForExport(String fileName, Map<String, Object> paramMap) {
        File reallyFile = downloadService.reallyExcel(fileName);

        boolean writeFlag = false;
        try {
            List<${className}Bo> ${classNameVariable}List = ${classNameVariable}Mapper.selectByMap(paramMap);
            EasyExcel.write(reallyFile, ${className}Bo.class).sheet(fileName).doWrite(${classNameVariable}List);
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
