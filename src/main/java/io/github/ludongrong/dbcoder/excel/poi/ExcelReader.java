package io.github.ludongrong.dbcoder.excel.poi;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import lombok.Getter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2007_POSTFIX = "xlsx";

    private List<Object> headRowCellList;

    @Getter
    private List<Map<String, Object>> dataList;

    public ExcelReader readExcel2007(InputStream inputStream) {

        this.dataList = new ArrayList<>();
        ExcelUtil.read07BySax(inputStream, -1, createRowHandler());
        return this;
    }

    public ExcelReader readExcel2003(InputStream inputStream) {

        this.dataList = new ArrayList<>();
        ExcelUtil.read03BySax(inputStream, -1, createRowHandler());
        return this;
    }

    private RowHandler createRowHandler() {
        return (sheetIndex, rowIndex, rowlist) -> {
            Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);

            // 取第一行充当表头，数据行的列不超过表头的列数。
            if (rowIndex == 0) {
                headRowCellList = rowlist;
                return;
            }

            Map<String, Object> rowData = new HashMap<>(headRowCellList.size());

            int cellSize;
            if (headRowCellList.size() > rowlist.size()) {
                cellSize = rowlist.size();
                rowData.putAll(getBeLeftData(rowlist));
            } else {
                cellSize = headRowCellList.size();
            }

            rowData.putAll(getCurrentRowData(rowlist, cellSize));

            dataList.add(rowData);
        };
    }

    private Map<String, String> getCurrentRowData(List<Object> rowlist, int cellSize) {
        Map<String, String> mapping = new HashMap<>();
        for (int i = 0; i < cellSize; i++) {
            String key = StrUtil.utf8Str(headRowCellList.get(i));
            String val = StrUtil.utf8Str(rowlist.get(i));
            mapping.put(key, val);
        }
        return mapping;
    }

    private Map<String, String> getBeLeftData(List<Object> rowlist) {
        Map<String, String> mapping = new HashMap<>();
        for (int i = rowlist.size(); i < headRowCellList.size(); i++) {
            String key = StrUtil.utf8Str(headRowCellList.get(i));
            mapping.put(key, null);
        }
        return mapping;
    }

}
