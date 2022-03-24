package io.github.ludongrong.dbcoder.provitor;

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
public class Project {

    @Getter
    @Setter
    private List<Table> tables;

    @Getter
    @Setter
    private String basePackage;

    @Getter
    @Setter
    private String projectName;

    @Getter
    @Setter
    private String dbType;

	public static Map<String, Object> toTestModel() {
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("basePackageDirectory", "testDirectory");
	    model.put("projectName", "dFD1");
	    model.put("className", "dFD");
	    model.put("basePackage", "aaa");
	    return model;
	}
}