package io.github.ludongrong.dbcoder.provitor;

import lombok.Getter;
import lombok.Setter;

/** 
* 列映射
*
* @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
* @since 2022-03-24
*/
public class ColumnMapping {

    /**
     * 本表列
     */
    @Getter
    @Setter
    private Column self;

    /**
     * 与其他表关联的列
     */
    @Getter
    @Setter
    private Column mapping;
}