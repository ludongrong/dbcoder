<#assign proName=projectName>
<#assign className=table.className>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.test.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${basepackage}.controller.${className}Controller;
import ${basepackage}.controller.dto.${className}Dto;
import ${basepackage}.model.${className};
import ${basepackage}.svc.I${className}Svc;

import cn.laocu.core.util.IdUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/app.xml",
		"classpath:conf/applicationContext-mvc.xml" })
public class ${className}ControllerTest
{
	private Logger logger = LogManager.getLogger(getClass());

	@Resource(name = "objectMapper")
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Mock
	private I${className}Svc ${classNameLower}Svc;

	@InjectMocks
	private ${className}Controller ${classNameLower}Controller;

	@Before
	public void setup()
	{
		logger.info("setup");
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(${classNameLower}Controller).build();
	}

	@Test
	public void create() throws Exception
	{
		${className} ${classNameLower} = new ${className}();
		<#list table.columns as column>
		<#if column.javaType == "String">
		<#if column.columnName == "Id">
		${classNameLower}.set${column.columnName}(IdUtil.stringUUID32());
		<#else>
		${classNameLower}.set${column.columnName}("1");
		</#if>
		<#elseif column.javaType == "BigDecimal">
		${classNameLower}.set${column.columnName}(0);
		<#elseif column.javaType == "int">
		${classNameLower}.set${column.columnName}(0);
		<#elseif column.javaType == "long">
		${classNameLower}.set${column.columnName}(0L);
		<#elseif column.javaType == "boolean">
		${classNameLower}.set${column.columnName}(false);
		<#elseif column.javaType == "Timestamp">
		<#if column.columnName != "CreateTime"&&column.columnName != "ModifyTime">
		${classNameLower}.set${column.columnName}(timestamp);
		</#if>
		<#elseif column.javaType == "Date">
		${classNameLower}.set${column.columnName}(timestamp);
		<#else>
		${classNameLower}.set${column.columnName}();
		</#if>
		</#list>
		Mockito.when(${classNameLower}Svc.create(Mockito.any(${className}.class)))
				.thenReturn(${classNameLower});

		${className}Dto ${classNameLower}Dto = new ${className}Dto();
		${classNameLower}Dto.set${className}(${classNameLower});

		mockMvc.perform(MockMvcRequestBuilders.post("/${classNameLower}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(${classNameLower}Dto)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("${classNameLower}.id")
						.value(${classNameLower}.getId()));
	}

	@Test
	public void delete() throws Exception
	{
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(IdUtil.stringUUID32());

		mockMvc.perform(MockMvcRequestBuilders.delete("/${classNameLower}/{id}",
				${classNameLower}.getId())).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value("0"));
	}

	@Test
	public void edit() throws Exception
	{
		${className} ${classNameLower} = new ${className}();

		${classNameLower}.setId(IdUtil.stringUUID32());

		${className}Dto ${classNameLower}Dto = new ${className}Dto();
		${classNameLower}Dto.set${className}(${classNameLower});

		mockMvc.perform(MockMvcRequestBuilders.put("/${classNameLower}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(${classNameLower}Dto)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value("0"));
	}

	@Test
	public void get() throws Exception
	{
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(IdUtil.stringUUID32());
		Mockito.when(${classNameLower}Svc.findById(Mockito.anyString()))
				.thenReturn(${classNameLower});

		mockMvc.perform(MockMvcRequestBuilders.get("/${classNameLower}/{id}",
				${classNameLower}.getId())).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
				.andExpect(MockMvcResultMatchers.jsonPath("${classNameLower}.id")
						.value(${classNameLower}.getId()));
	}
}