package io.github.ludongrong.dbcoder.sql;

import java.util.List;
import java.util.Map;

public class ConditionOptional extends SqlElement {

    private SqlElementContainer container;

    private String paramName;

    private String columnName;

    private String oper;

    private boolean paramValueRequired;

    public ConditionOptional() {
        super();
    }

    public ConditionOptional(String columnName, String paramName, String oper, boolean paramValueRequired) {
        super();
        this.columnName = columnName;
        this.paramName = paramName;
        this.oper = oper;
        this.paramValueRequired = paramValueRequired;
    }

    public void setContainer(SqlElementContainer container) {
        this.container = container;
    }

    public SqlElementContainer getContainer() {
        return container;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getOper() {
        return "";
    }

    private boolean appendNoParamSql(StringBuilder sql, Map<String, Object> param, String columnName) {
        if (param.containsKey(paramName)) {
            sql.append(columnName).append(' ').append(oper);
            return true;
        }
        return false;
    }

    private boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args, String columnName) {
        // 不需要参数值
        if (!paramValueRequired) {
            return appendNoParamSql(sql, param, columnName);
        }

        // 获取参数值
        Object p = param.get(paramName);
        if (p == null) {
            return false;
        }

        // 参数个数
        int paramCount = 0;

        // 需要遍历参数值,添加到返回参数列表
        // 标记参数个数,后面拼写sql时候需要用到
        if (p instanceof List) {/* 列表 */
            for (Object val : (List<?>) p) {
                args.add(val);
                paramCount++;
            }
        } else if (p instanceof Object[]) {/* 数组 */
            for (Object val : (Object[]) p) {
                args.add(val);
                paramCount++;
            }
        } else {
            paramCount = 1;
            args.add(p);
        }

        // 参数等于0,拼写失败
        if (paramCount == 0) {
            return false;
        }

        // 拼sql
        sql.append(columnName).append(' ').append(oper).append(' ');
        if (this.oper.equals("BETWEEN")) {
            if (paramCount != 2) {
                throw new IllegalConditionException("Between size error");
            }
            sql.append(" ? AND ? ");
        } else if (paramCount == 1) {/* 参数个数是1个时候,IN,NOT IN需要添加括号 */
            if (this.oper.equals("IN") || this.oper.equals("NOT IN")) {
                sql.append("( ? )");
            } else {
                sql.append(" ? ");
            }
        } else {/* 参数个数是多个时候,肯定有括号 */
            sql.append("(");
            for (int i = 0; i < paramCount; i++) {
                sql.append("?,");
            }
            sql.setLength(sql.length() - 1);
            sql.append(") ");
        }

        // 拼写成功
        return true;
    }

    @Override
    public boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args) {
        return appendSql(sql, param, args, columnName);
    }

    @Override
    public boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args,
            Map<String, String> columnMap) {
        String col = columnMap.get(columnName);
        if (null == col) {
            return false;
        }
        return appendSql(sql, param, args, col);
    }

    private boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args,
            String columnName) {
        // 不需要参数值
        if (!paramValueRequired) {
            return appendNoParamSql(sql, param, columnName);
        }

        // 获取参数值
        Object p = param.get(paramName);
        if (p == null) {
            return false;
        }

        // 参数等于0,拼写失败
        int paramCount = 0;

        // 参数值是列表或数组,获取参数个数
        if (p instanceof List) {
            paramCount = ((List<?>) p).size();
        } else if (p instanceof Object[]) {
            paramCount = ((Object[]) p).length;
        } else {
            paramCount = 1;
        }

        // 参数等于0,拼写失败
        if (paramCount == 0) {
            return false;
        }

        // 返回参数值
        args.put(paramName, p);

        // 拼sql
        sql.append(columnName).append(' ').append(oper).append(' ');
        if (paramCount == 1) {/* 参数个数是1个时候 */
            if (this.oper.equals("IN") || this.oper.equals("NOT IN")) {/* 需要添加括号 */
                sql.append("(:").append(paramName).append(") ");
            } else {/* 不需要添加括号 */
                sql.append(" :").append(paramName);
            }
        } else {/* 参数个数是多个时候,需要添加括号 */
            sql.append("(:").append(paramName).append(") ");
        }

        // 拼写成功
        return true;
    }

    @Override
    public boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args) {
        return appendNamedSql(sql, param, args, columnName);
    }

    @Override
    public boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args,
            Map<String, String> columnMap) {
        String col = columnMap.get(columnName);
        if (null == col) {
            return false;
        }
        return appendNamedSql(sql, param, args, col);
    }
}
