<#assign proName=projectName>
<#assign className=table.className>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.test.svc;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ${basepackage}.model.${className};
import ${basepackage}.svc.I${className}Svc;

import cn.hutool.core.lang.Assert;
import cn.laocu.core.util.CoreUtil;
import cn.laocu.core.util.IdUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/app.xml")
public class ${className}SvcTest
{
	private Logger logger = LogManager.getLogger(getClass());

	@Resource(name="${proName}.${classNameLower}Svc")
	private I${className}Svc ${classNameLower}Svc;

	@Test
	public void create()
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		${className} ${classNameLower} = new ${className}();
		
		<#list table.columns as column>
			<#if column.javaType == "String">
				<#if column.columnName != "Id">
		${classNameLower}.set${column.columnName}("1");
				</#if>
			<#elseif column.javaType == "BigDecimal">
		${classNameLower}.set${column.columnName}(BigDecimal.valueOf(0));
			<#elseif column.javaType == "int">
		${classNameLower}.set${column.columnName}(0);
			<#elseif column.javaType == "long">
		${classNameLower}.set${column.columnName}(0L);
			<#elseif column.javaType == "boolean">
		${classNameLower}.set${column.columnName}(false);
			<#elseif column.javaType == "Timestamp">
				<#if column.columnName != "CreateTime" && column.columnName != "ModifyTime">
		${classNameLower}.set${column.columnName}(timestamp);
				</#if>
			<#elseif column.javaType == "Date">
		${classNameLower}.set${column.columnName}(new Date());
			<#else>
		${classNameLower}.set${column.columnName}();
			</#if>
		</#list>
		
		${classNameLower}Svc.create(${classNameLower});
		Assert.notNull(${classNameLower}Svc.findById(${classNameLower}.getId()));
		${classNameLower}Svc.deleteById(${classNameLower}.getId());
		Assert.isNull(${classNameLower}Svc.findById(${classNameLower}.getId(), null));
	}
	
	@Test
	public void createTestData()
	{
		ExcelReader reader = ExcelUtil
				.getReader(ResourceUtil.getStream("${table.sqlName}.xlsx"), 0);
		List<List<Object>> readAll = reader.read();
		logger.info("size={}", readAll.size());
		
		for (List<Object> objList : readAll)
		{
			if (objList.size() < ${table.columns?size})
			{
				for (int i = objList.size(); i < ${table.columns?size}; i++)
				{
					objList.add(null);
				}
			}
			
			//ThreadUtil.safeSleep(1000);

			${className} ${classNameLower} = new ${className}();
			
			<#list table.columns as column>
				<#if column.javaType == "String">
			${classNameLower}.set${column.columnName}(CoreUtil.toString(objList.get(${column_index}), null));
				<#elseif column.javaType == "BigDecimal">
			${classNameLower}.set${column.columnName}(new BigDecimal(CoreUtil.toString(objList.get(${column_index}), "0")));
				<#elseif column.javaType == "int">
			${classNameLower}.set${column.columnName}(CoreUtil.toInt(objList.get(${column_index}), 0));
				<#elseif column.javaType == "long">
			${classNameLower}.set${column.columnName}(CoreUtil.toLong(objList.get(${column_index}), 0));
				<#elseif column.javaType == "boolean">
			${classNameLower}.set${column.columnName}(CoreUtil.toBoolean(objList.get(${column_index}), false));
				<#elseif column.javaType == "Timestamp">
					<#if column.columnName != "CreateTime" && column.columnName != "ModifyTime">
			${classNameLower}.set${column.columnName}(DateUtil.parseDateByDefaultValue(objList.get(${column_index}), Timestamp.class, null));
					</#if>
				<#elseif column.javaType == "Date">
			${classNameLower}.set${column.columnName}(DateUtil.parseDateByDefaultValue(objList.get(${column_index}), Date.class, null));
				<#else>
			${classNameLower}.set${column.columnName}();
				</#if>
			</#list>

			if (StrUtil.isNotEmpty(${classNameLower}.getId()))
			{
				${className} ${classNameLower}DB = ${classNameLower}Svc.findById(${classNameLower}.getId(),null);
				if (ObjectUtil.isNull(${classNameLower}DB))
				{
					${classNameLower}Svc.create(${classNameLower});
				} else
				{
					${classNameLower}Svc.update(${classNameLower});
				}
			} else
			{
				${classNameLower}Svc.create(${classNameLower});
			}
		}
	}
	
	@Test
	public void deleteById()
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		${className} ${classNameLower} = new ${className}();

		<#list table.columns as column>
			<#if column.javaType == "String">
				<#if column.columnName != "Id">
		${classNameLower}.set${column.columnName}("1");
				</#if>
			<#elseif column.javaType == "BigDecimal">
		${classNameLower}.set${column.columnName}(BigDecimal.valueOf(0));
			<#elseif column.javaType == "int">
		${classNameLower}.set${column.columnName}(0);
			<#elseif column.javaType == "long">
		${classNameLower}.set${column.columnName}(0L);
			<#elseif column.javaType == "boolean">
		${classNameLower}.set${column.columnName}(false);
			<#elseif column.javaType == "Timestamp">
				<#if column.columnName != "CreateTime" && column.columnName != "ModifyTime">
		${classNameLower}.set${column.columnName}(timestamp);
				</#if>
			<#elseif column.javaType == "Date">
		${classNameLower}.set${column.columnName}(timestamp);
			<#else>
		${classNameLower}.set${column.columnName}();
			</#if>
		</#list>

		${classNameLower}Svc.create(${classNameLower});
		Assert.notNull(${classNameLower}Svc.findById(${classNameLower}.getId()));
		${classNameLower}Svc.deleteById(${classNameLower}.getId());
		Assert.isNull(${classNameLower}Svc.findById(${classNameLower}.getId(), null));
	}
	
	@Test
	public void update()
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		${className} ${classNameLower} = new ${className}();

		<#list table.columns as column>
			<#if column.javaType == "String">
				<#if column.columnName != "Id">
		${classNameLower}.set${column.columnName}("1");
				</#if>
			<#elseif column.javaType == "BigDecimal">
		${classNameLower}.set${column.columnName}(BigDecimal.valueOf(0));
			<#elseif column.javaType == "int">
		${classNameLower}.set${column.columnName}(0);
			<#elseif column.javaType == "long">
		${classNameLower}.set${column.columnName}(0L);
			<#elseif column.javaType == "boolean">
		${classNameLower}.set${column.columnName}(false);
			<#elseif column.javaType == "Timestamp">
				<#if column.columnName != "CreateTime" && column.columnName != "ModifyTime">
		${classNameLower}.set${column.columnName}(timestamp);
				</#if>
			<#elseif column.javaType == "Date">
		${classNameLower}.set${column.columnName}(timestamp);
			<#else>
		${classNameLower}.set${column.columnName}();
			</#if>
		</#list>

		${classNameLower}Svc.create(${classNameLower});
		timestamp = ${classNameLower}.getModifyTime();
		Assert.notNull(${classNameLower}Svc.findById(${classNameLower}.getId()));
		${classNameLower}.setModifyTime(
				new Timestamp(System.currentTimeMillis() + 1000));
		${classNameLower}Svc.update(${classNameLower});
		${classNameLower} = ${classNameLower}Svc.findById(${classNameLower}.getId());
		Assert.state(
				${classNameLower}.getModifyTime().getTime() != timestamp.getTime());
		${classNameLower}Svc.deleteById(${classNameLower}.getId());
		Assert.isNull(${classNameLower}Svc.findById(${classNameLower}.getId(), null));
	}
}