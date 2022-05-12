package io.github.ludongrong.dbcoder.pdm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/** 
* Project
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class PDMProject {

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
     * 数据库类型
     */
    @Getter
    @Setter
    private String dbType;
    
    /**
     * 数据库的表
     */
    @Getter
    @Setter
    private List<Table> tables;

	public static Map<String, Object> toTestModel() {
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("basePackageDirectory", "testDirectory");
	    model.put("projectName", "dFD1");
	    model.put("className", "dFD");
	    model.put("basePackage", "aaa");
	    return model;
	}
}