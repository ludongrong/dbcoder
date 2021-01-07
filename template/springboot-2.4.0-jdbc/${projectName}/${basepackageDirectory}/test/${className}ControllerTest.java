package ${basepackage}.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import io.github.ludongrong.dbcoder.controller.${className}Controller;
import io.github.ludongrong.dbcoder.entity.$
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.service.I${className}Service;

@WebMvcTest(${className}Controller.class)
public class ${className}ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "dbcoder-${className}ServiceImpl")
    private I${className}Service ${classNameVariable}Service;

    @Test
    void createTest() throws Exception {

        BDDMockito.given(this.${classNameVariable}Service.create(BDDMockito.any(${className}Bo.class))).willReturn(true);

        JSONObject json = new JSONObject();
    <#list columns as column>
        json.put("${column.javaNameVariable}", "${column.javaNameVariable}");
    </#list>

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.post("/${classNameVariable?lower_case}")
                        .content(json.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(result);
    }

    @Test
    void createUploadTest() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "1.pdm", null,
                new FileInputStream(new File("C:\\Users\\Think\\Desktop\\1.pdm")));

        BDDMockito.given(this.${classNameVariable}Service.create(BDDMockito.any(${className}Bo.class))).willReturn(true);

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.multipart("/${classNameVariable?lower_case}")
                        .file(mockMultipartFile)
                    <#list columns as column>
                        .param("${column.javaNameVariable}", "${column.javaNameVariable}")
                    </#list>
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(result);
    }

    @Test
    void deleteTest() throws Exception {

        BDDMockito.given(this.pdFileService.delete(BDDMockito.anyString())).willReturn(1);

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.delete("/${classNameVariable?lower_case}/" + UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(result);
    }

    @Test
    void getTest() throws Exception {

        ${className}Bo ${classNameVariable}Bo = new ${className}Bo();
        ${classNameVariable}Bo.setId(UUID.randomUUID().toString());

        BDDMockito.given(this.${classNameVariable}Service.list(BDDMockito.anyMap())).willReturn(Arrays.asList(${classNameVariable}Bo));

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.get("/${classNameVariable?lower_case}")
                        .param("id", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(result);
    }

    @Test
    void getDownTest() throws Exception {

        String id = UUID.randomUUID().toString();
        ${className}Bo ${classNameVariable}Bo = new ${className}Bo();
        ${classNameVariable}Bo.setId(id);

        BDDMockito.given(this.${classNameVariable}Service.list(BDDMockito.anyMap())).willReturn(Arrays.asList(${classNameVariable}Bo));

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.get("/${classNameVariable?lower_case}/xlsx")
                        .param("id", "111")
                        .accept("application/x-msdownload"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/x-msdownload")).andReturn();

        InputStream contentInStream = new ByteArrayInputStream(mvcResult.getResponse().getContentAsByteArray());
        ExcelReader reader = ExcelUtil.getReader(contentInStream);
        List<List<Object>> readAll = reader.read();
        Object obj = readAll.get(0).get(0);
        Assert.assertTrue(obj instanceof String);
        Assert.assertNotNull(obj.toString().equals(id));
    }
}