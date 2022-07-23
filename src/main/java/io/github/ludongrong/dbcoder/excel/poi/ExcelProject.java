package io.github.ludongrong.dbcoder.excel.poi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * ExcelProject
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-24
 */
public class ExcelProject {

    /**
     * 数据
     */
    @Getter
    @Setter
    private List<Map<String, Object>> modelList;

}
