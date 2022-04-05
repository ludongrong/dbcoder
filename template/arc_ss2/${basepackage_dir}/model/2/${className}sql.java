public static final class _COLUMN
{
	<#list table.notPFkColumns as column>
	public static String ${column.columnNameLower} = "${column.sqlName}";
	</#list>
}

public static final class _COLUMN_TYPE
{
	<#list table.notPFkColumns as column>
	public static String ${column.columnNameLower} = "${column.sqlTypeName}";
	</#list>
}

public static final class _FIELD
{
	<#list table.notPFkColumns as column>
	public static String ${column.columnNameLower} = "${column.columnNameLower}";
	</#list>
}