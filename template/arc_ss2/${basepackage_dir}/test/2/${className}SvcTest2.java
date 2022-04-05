<#assign proName = projectName>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
@Test
public void create_list()
{
	List<${className}> ${classNameLower}List = new ArrayList<${className}>();
	
	${className} ${classNameLower} = new ${className}();
	
<#list table.columns as column>
<#if column.javaType == "String">
	${classNameLower}.set${column.columnName}("");
<#elseif column.javaType == "BigDecimal">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "int">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "long">
	${classNameLower}.set${column.columnName}(0L);
<#elseif column.javaType == "Timestamp">
	${classNameLower}.set${column.columnName}(new Timestamp(System.currentTimeMillis()));
<#elseif column.javaType == "Date">
	${classNameLower}.set${column.columnName}(new Date(System.currentTimeMillis()));
<#else>
	${classNameLower}.set${column.columnName}();
</#if>
</#list>
	
	${classNameLower}List.add(${classNameLower});
	
	${classNameLower}Svc.create(${classNameLower}List);
}

@Test
public void destroy()
{
	String id = "f1458887a2c3469f8cf74a37cccc1737";
	${classNameLower}Svc.destroy(id);
}

@Test
public void update()
{
	${className} ${classNameLower} = new ${className}();
	
<#list table.columns as column>
<#if column.javaType == "String">
	${classNameLower}.set${column.columnName}("");
<#elseif column.javaType == "BigDecimal">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "int">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "long">
	${classNameLower}.set${column.columnName}(0L);
<#elseif column.javaType == "Timestamp">
	${classNameLower}.set${column.columnName}(new Timestamp(System.currentTimeMillis()));
<#elseif column.javaType == "Date">
	${classNameLower}.set${column.columnName}(new Date(System.currentTimeMillis()));
<#else>
	${classNameLower}.set${column.columnName}();
</#if>
</#list>
	
	${classNameLower}.setId("f1458887a2c3469f8cf74a37cccc1737");
	
	${classNameLower}Svc.update(${classNameLower});
}

@Test
public void update_list()
{
	List<${className}> ${classNameLower}List = new ArrayList<${className}>();
	
	${className} ${classNameLower} = new ${className}();
	
<#list table.columns as column>
<#if column.javaType == "String">
	<#if column.columnName == "Id" || column.columnName == "Oid">
	${classNameLower}.set${column.columnName}(Identifier.stringUUID());
	<#else>
	${classNameLower}.set${column.columnName}("");
	</#if>
<#elseif column.javaType == "BigDecimal">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "int">
	${classNameLower}.set${column.columnName}(0);
<#elseif column.javaType == "long">
	${classNameLower}.set${column.columnName}(0L);
<#elseif column.javaType == "Timestamp">
	${classNameLower}.set${column.columnName}(new Timestamp(System.currentTimeMillis()));
<#elseif column.javaType == "Date">
	${classNameLower}.set${column.columnName}(new Date(System.currentTimeMillis()));
<#else>
	${classNameLower}.set${column.columnName}();
</#if>
</#list>
	
	${classNameLower}.setId("f1458887a2c3469f8cf74a37cccc1737");
	
	${classNameLower}List.add(${classNameLower});

	${classNameLower}Svc.update(${classNameLower}List);
}

@Test
public void detail()
{
	String id = "f1458887a2c3469f8cf74a37cccc1737";
	${className} ${classNameLower} = ${classNameLower}Svc.detail(id);
}

@Test
public void count()
{
	int num = ${classNameLower}Svc.count();
	logger.info(num);
}
