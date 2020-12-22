package io.github.ludongrong.dbcoder.sql;

import java.util.List;
import java.util.Map;

public abstract class SqlElement {

    private SqlElementContainer parent;

    public abstract boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args);

    public abstract boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args,
            Map<String, String> columnMap);

    public abstract boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args);

    public abstract boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args,
            Map<String, String> columnMap);

    public void setParent(SqlElementContainer parent) {
        this.parent = parent;
    }

    public SqlElementContainer getParent() {
        return parent;
    }
}
