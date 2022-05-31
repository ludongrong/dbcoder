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
 * ${Name} 业务逻辑实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Service("${CodeCamelFirstLower}Service")
public class ${CodeCamelFirstUpper}ServiceImpl implements ${CodeCamelFirstUpper}Service {
    
	@Resource
    private ${CodeCamelFirstUpper}Mapper ${CodeCamelFirstLower}Mapper;
	
	@Resource
    private DownloadService downloadService;
	
    // @Transactional
	
	@Override
    public PageInfo<${CodeCamelFirstUpper}Entity> queryPage(Map<String, Object> paramMap) {
        Page.startPage(paramMap);
        return new PageInfo<>(${CodeCamelFirstLower}Mapper.queryList(paramMap));
    }
	
    @Override
    public List<${CodeCamelFirstUpper}Entity> queryList(Map<String, Object> paramMap) {
        return ${CodeCamelFirstLower}Mapper.queryList(paramMap);
    }
	
	@Async
    @Override
    public String queryListForExport(String fileName, Map<String, Object> paramMap) {
        File reallyFile = downloadService.reallyExcel(fileName);

        boolean writeFlag = false;
        try {
            List<${CodeCamelFirstUpper}Entity> ${CodeCamelFirstLower}List = ${CodeCamelFirstLower}Mapper.queryList(paramMap);
            EasyExcel.write(reallyFile, ${CodeCamelFirstUpper}Entity.class).sheet(fileName).doWrite(${CodeCamelFirstLower}List);
            writeFlag = true;
        } catch (Exception e) {
            writeFlag = false;
            log.error("生成{}失败，错误信息：{}", fileName, e.getMessage(), e);
        }

        return downloadService.ok(reallyFile, writeFlag);
    }
    
}
