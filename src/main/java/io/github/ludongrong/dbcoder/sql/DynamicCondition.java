package io.github.ludongrong.dbcoder.sql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

public class DynamicCondition extends ConditionGroup {

    /**
     * ASC
     */
    private final static String ASC = "asc";

    /**
     * PRE_WHERE
     */
    private final static String PRE_WHERE = "where";

    /**
     * PRE_AND
     */
    private final static String PRE_AND = "and";

    /**
     * PRE_OR
     */
    private final static String PRE_OR = "or";

    /**
     * pre
     */
    private String pre;

    /**
     * obMap
     */
    private Map<String, String> obMap = new HashMap<String, String>();

    /**
     * columnMap
     */
    Map<String, String> columnMap = new LinkedHashMap<>();

    public DynamicCondition() {
        super(ConditionGroup.AND);
    }

    public DynamicCondition(String groupType) {
        super(groupType);
    }

    public DynamicCondition(String groupType, String depend) {
        super(groupType, depend);
    }

    public DynamicCondition(String pre, String groupType, String depend) {
        super(groupType, depend);
    }

    public DynamicCondition preWhere() {
        this.pre = PRE_WHERE;
        return this;
    }

    public DynamicCondition preAnd() {
        this.pre = PRE_AND;
        return this;
    }

    public DynamicCondition preOr() {
        this.pre = PRE_OR;
        return this;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    public boolean generateSql(StringBuilder sqlBuilder, Map<String, Object> param, List<Object> args) {
        StringBuilder generated = new StringBuilder();
        appendSql(generated, param, args);
        return generated(sqlBuilder, generated, param);
    }

    public boolean generateSql(StringBuilder sqlBuilder, Map<String, Object> param, List<Object> args,
            Map<String, String> columnMap) {
        StringBuilder generated = new StringBuilder();
        appendSql(generated, param, args, columnMap);
        return generated(sqlBuilder, generated, param);
    }

    private boolean generated(StringBuilder sqlBuilder, StringBuilder generated, Map<String, Object> param) {
        if (generated.length() > 0) {
            // 删除俩边的括号符
            generated.deleteCharAt(0);
            generated.deleteCharAt(generated.length() - 1);

            // 前缀
            sqlBuilder.append(" ");

            if (StrUtil.isEmpty(this.pre) == false) {
                sqlBuilder.append(this.pre);
                sqlBuilder.append(" ");
            }
            sqlBuilder.append(generated);

            // 排序
            if (param != null) {
                appendOrderBy(sqlBuilder, param);
            }

            return true;
        }
        return false;
    }

    public void orderByCreated(String columnName, String paramName) {
        obMap.put(paramName, columnName);
    }

    private void appendOrderBy(StringBuilder sql, Map<String, Object> param) {
        boolean firstexec = true;
        for (Map.Entry<String, String> entry : obMap.entrySet()) {
            Object orderparam = param.get(entry.getKey());
            if (orderparam == null) {
                continue;
            }

            if (entry.getValue() == null) {
                continue;
            }

            if (firstexec) {
                sql.append(" ORDER BY ");
                firstexec = false;
            } else {
                sql.append(",");
            }
            sql.append(entry.getValue());

            if (!ASC.equalsIgnoreCase((String) orderparam)) {
                sql.append(" DESC");
            }
        }
    }

    static public String generate(String sql, DynamicCondition dyc, Map<String, Object> input, List<Object> args) {

        StringBuilder strBuf = new StringBuilder(sql);

        if (MapUtil.isNotEmpty(input)) {
            boolean genresult = dyc.preWhere().generateSql(strBuf, input, args);
            if (!genresult) {
                throw new IllegalConditionException("Generate where sql, but return empty result");
            }
        }

        return strBuf.toString();
    }
}
