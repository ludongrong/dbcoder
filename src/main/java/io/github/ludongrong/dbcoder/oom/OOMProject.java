package io.github.ludongrong.dbcoder.oom;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
* Project
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-05-09
*/
public class OOMProject {

	/**
	 * 工程名
	 */
	@Getter
    @Setter
    private String projectName;
	
    /**
     * 工程包
     */
    @Getter
    @Setter
    private String basePackage;
    
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date currentDate;
    
    /**
     * 接口
     */
    @Getter
    @Setter
    private List<Map<String, Object>> interfaces;

}