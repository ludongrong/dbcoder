package io.github.ludongrong.dbcoder.provitor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/** 
* 表与列的抽象部分
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class Element implements Serializable {

	private static final long serialVersionUID = -4708191193480564231L;

	/**
	 * 标识
	 */
	@Getter
    @Setter
    private String id;

    /**
     * 名称
     * <pre>列名或者表的解释名称</pre>
     */
    @Getter
    @Setter
    private String name;
    
    /**
     * 名称
     * <pre>列名或者表的字段</pre>
     */
    @Getter
    @Setter
    private String code;
    
}