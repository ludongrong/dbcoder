package io.github.ludongrong.dbcoder.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConditionGroup extends SqlElementContainer {

    public final static String AND = "AND";

    public final static String OR = "OR";

    private String groupType;

    private List<SqlElement> conds;

    private String depend;

    public ConditionGroup(String groupType) {
        this(groupType, null);
    }

    public ConditionGroup(String groupType, String depend) {
        super();
        assert (AND.equalsIgnoreCase(groupType) || OR.equalsIgnoreCase(groupType));
        this.groupType = groupType;
        this.conds = new ArrayList<SqlElement>();
        this.depend = depend;
    }

    @Override
    public boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args) {
        if (checkDepend(param) == false) {
            return false;
        }

        StringBuilder groupSql = new StringBuilder();
        for (SqlElement e : conds) {
            // 例子:
            // 先拼 column=val 再拼 and,最后的groupSql就是 column=val and
            if (e.appendSql(groupSql, param, args)) {
                groupSql.append(' ').append(groupType).append(' ');
            }
        }

        return delLastGroupType(sql, groupSql);
    }

    @Override
    public boolean appendSql(StringBuilder sql, Map<String, Object> param, List<Object> args,
            Map<String, String> columnMap) {
        if (checkDepend(param) == false) {
            return false;
        }

        StringBuilder groupSql = new StringBuilder();
        for (SqlElement e : conds) {
            // 例子:
            // 先拼 column=val 再拼 and,最后的groupSql就是 column=val and
            if (e.appendSql(groupSql, param, args, columnMap)) {
                groupSql.append(' ').append(groupType).append(' ');
            }
        }

        return delLastGroupType(sql, groupSql);
    }

    @Override
    public boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args) {
        if (checkDepend(param) == false) {
            return false;
        }

        StringBuilder groupSql = new StringBuilder();
        for (SqlElement e : conds) {
            if (e.appendNamedSql(groupSql, param, args)) {
                groupSql.append(' ').append(groupType).append(' ');
            }
        }

        return delLastGroupType(sql, groupSql);
    }

    @Override
    public boolean appendNamedSql(StringBuilder sql, Map<String, Object> param, Map<String, Object> args,
            Map<String, String> columnMap) {
        if (checkDepend(param) == false) {
            return false;
        }

        StringBuilder groupSql = new StringBuilder();
        for (SqlElement e : conds) {
            if (e.appendNamedSql(groupSql, param, args, columnMap)) {
                groupSql.append(' ').append(groupType).append(' ');
            }
        }

        return delLastGroupType(sql, groupSql);
    }

    private boolean checkDepend(Map<String, Object> param) {
        if (depend != null && !param.containsKey(depend)) {
            return false;
        }
        return true;
    }

    private boolean delLastGroupType(StringBuilder sql, StringBuilder groupSql) {
        if (groupSql.length() > 0) {
            groupSql.setLength(groupSql.length() - groupType.length() - 1);
            sql.append('(').append(groupSql).append(')');
            return true;
        }
        return false;
    }

    @Override
    public void sqlElementCreated(SqlElement element) {
        conds.add(element);
        element.setParent(this);
    }
}
