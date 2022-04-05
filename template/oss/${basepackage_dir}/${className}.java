INSERT INTO `eova_object` VALUES ('${table.sqlNameLowerCase}', '${table.tableAlias}', null, '${table.sqlNameLowerCase}', 'id', 'main', '1', '0', '1', '1', null, null, null, null, null);

<#list table.notPFkColumns as column>
INSERT INTO `eova_field` VALUES ('${table.sqlNameLowerCase}', 'null', '${column.getSqlNameLowerCase}', '${column.columnAlias}', '0', 'string', '文本框', '22', null, '0', '1', '0', '0', '0', '0', '0', '0', '0', '${column.remarks}', null, null, null, '130', '20', null);
</#list>
