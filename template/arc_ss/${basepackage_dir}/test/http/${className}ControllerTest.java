<#assign proName=projectName>
<#assign className=table.className>
<#assign classNameLower=className?uncap_first>
package ${basepackage}.test.http;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ${basepackage}.controller.dto.${className}Dto;
import ${basepackage}.model.${className};
import ${basepackage}.model.dyna.${className}Dyna;
import com.nsn.web.util.RestClient;

import cn.hutool.core.lang.Assert;
import cn.laocu.core.util.IdUtil;

public class ${className}ControllerTest
{
	private Logger logger = LogManager.getLogger(getClass());

	public static ObjectMapper objectMapper = new ObjectMapper();
	static
	{
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setLenient(false);
		objectMapper.setDateFormat(simpleDateFormat);
	}

	@Test
	public void create() throws Exception
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

		${className}Dto dto = new ${className}Dto();
		dto.set${className}(${classNameLower});

		String dtoStr = objectMapper.writeValueAsString(dto);
		logger.info("dtoStr={}", dtoStr);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=1w5y4srwa19ywaf5mgqeo3df1");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.post("/${classNameLower}.do", dtoStr, headers);

		logger.info("requestBody={}", requestBody);
		dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}

	@Test
	public void delete() throws Exception
	{
		String id = IdUtil.stringUUID32();

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=1w5y4srwa19ywaf5mgqeo3df1");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.delete("/${classNameLower}/" + id + ".do", headers);

		logger.info("requestBody={}", requestBody);
		${className}Dto dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}

	@Test
	public void edit() throws Exception
	{
		${className} ${classNameLower} = new ${className}();
		${classNameLower}.setId(IdUtil.stringUUID32());
		${className}Dto dto = new ${className}Dto();
		dto.set${className}(${classNameLower});

		String dtoStr = objectMapper.writeValueAsString(dto);
		logger.info("dtoStr={}", dtoStr);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=1w5y4srwa19ywaf5mgqeo3df1");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.put("/${classNameLower}.do", dtoStr, headers);

		logger.info("requestBody={}", requestBody);
		dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}

	@Test
	public void get() throws Exception
	{
		String id = "8dfbc79c88854a148014af7abfba47f1";

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=eelrglhy0yeg1sl80v79i2x9b");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.get("/${classNameLower}/" + id + ".do", null, headers);

		logger.info("requestBody={}", requestBody);
		${className}Dto dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}

	@Test
	public void find() throws Exception
	{
		${className}Dyna ${classNameLower}Dyna = new ${className}Dyna();
		${classNameLower}Dyna.setIdEq(IdUtil.stringUUID32());
		${className}Dto dto = new ${className}Dto();
		dto.set${className}Dyna(${classNameLower}Dyna);

		String dtoStr = objectMapper.writeValueAsString(dto);
		logger.info("dtoStr={}", dtoStr);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=1w5y4srwa19ywaf5mgqeo3df1");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.post("/${classNameLower}/list.do", dtoStr, headers);

		logger.info("requestBody={}", requestBody);
		dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}

	@Test
	public void findPage() throws Exception
	{
		${className}Dyna ${classNameLower}Dyna = new ${className}Dyna();
		${classNameLower}Dyna.setIdEq(IdUtil.stringUUID32());
		${className}Dto dto = new ${className}Dto();
		dto.setPageNum(1);
		dto.setPageSize(1);
		dto.set${className}Dyna(${classNameLower}Dyna);

		String dtoStr = objectMapper.writeValueAsString(dto);
		logger.info("dtoStr={}", dtoStr);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=1w5y4srwa19ywaf5mgqeo3df1");

		RestClient client = new RestClient("127.0.0.1", 80);
		String requestBody = client.post("/${classNameLower}/page.do", dtoStr, headers);

		logger.info("requestBody={}", requestBody);
		dto = objectMapper.readValue(requestBody, ${className}Dto.class);

		Assert.state(dto.getCode().equals("0"));

		logger.info("dtoStr={}", objectMapper.writeValueAsString(dto));
		logger.info("ok");
	}
}