package io.github.ludongrong.dbcoder.sql;

public abstract class SqlElementContainer extends SqlElement {

    private static final String EQ = "=";

    private static final String NE = "!=";

    private static final String GT = ">";

    private static final String GE = ">=";

    private static final String LT = "<";

    private static final String LE = "<=";

    abstract public void sqlElementCreated(SqlElement element);

    public void orderByCreated(String columnName, String paramName) {
        SqlElementContainer c = getParent();
        while (c.getParent() != null) {
            c = c.getParent();
        }
        c.orderByCreated(columnName, paramName);
    }

    public SqlElementContainer eq(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, EQ, true));
        return this;
    }

    public SqlElementContainer ne(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, NE, true));
        return this;
    }

    public SqlElementContainer gt(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, GT, true));
        return this;
    }

    public SqlElementContainer ge(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, GE, true));
        return this;
    }

    public SqlElementContainer lt(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, LT, true));
        return this;
    }

    public SqlElementContainer le(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, LE, true));
        return this;
    }

    public SqlElementContainer like(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "LIKE", true));
        return this;
    }

    public SqlElementContainer notLike(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "NOT LIKE", true));
        return this;
    }

    public SqlElementContainer in(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "IN", true));
        return this;
    }

    public SqlElementContainer notIn(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "NOT IN", true));
        return this;
    }

    public SqlElementContainer isNull(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "IS NULL", false));
        return this;
    }

    public SqlElementContainer isNotNull(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "IS NOT NULL", false));
        return this;
    }

    public SqlElementContainer between(String columnName, String paramName) {
        sqlElementCreated(new ConditionOptional(columnName, paramName, "BETWEEN", true));
        return this;
    }

    public SqlElementContainer orGroup() {
        ConditionGroup group = new ConditionGroup(ConditionGroup.OR);
        sqlElementCreated(group);
        return group;
    }

    public SqlElementContainer andGroup() {
        ConditionGroup group = new ConditionGroup(ConditionGroup.AND);
        sqlElementCreated(group);
        return group;
    }

    public SqlElementContainer orGroup(String dependParam) {
        ConditionGroup group = new ConditionGroup(ConditionGroup.OR, dependParam);
        sqlElementCreated(group);
        return group;
    }

    public SqlElementContainer andGroup(String dependParam) {
        ConditionGroup group = new ConditionGroup(ConditionGroup.AND, dependParam);
        sqlElementCreated(group);
        return group;
    }

    public SqlElementContainer ob(String columnName, String paramName) {
        orderByCreated(columnName, paramName);
        return this;
    }
}
