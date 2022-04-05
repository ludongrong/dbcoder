package ${basePackage}.service.impl;

import com.alibaba.excel.EasyExcel;

import ${basePackage}.service.DownloadService;
import ${basePackage}.dao.${className}Mapper;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.service.${className}Service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * ${tableNameCN} 业务逻辑实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Service("${classNameVariable}Service")
public class ${className}ServiceImpl implements ${className}Service {
    
	@Resource
    private ${className}Mapper ${classNameVariable}Mapper;
	
	@Resource
    private DownloadService downloadService;
	
    // @Transactional
	
	@Override
    public PageInfo<${className}Entity> queryPage(Map<String, Object> paramMap) {
        Page.startPage(paramMap);
        return new PageInfo<>(${classNameVariable}Mapper.queryList(paramMap));
    }
	
    @Override
    public List<${className}Entity> queryList(Map<String, Object> paramMap) {
        return ${classNameVariable}Mapper.queryList(paramMap);
    }
	
	@Async
    @Override
    public String queryListForExport(String fileName, Map<String, Object> paramMap) {
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
    
}
