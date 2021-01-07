package io.github.ludongrong.dbcoder.test;

import java.io.File;
import java.io.FileInputStream;

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

import io.github.ludongrong.dbcoder.controller.PdFileController;
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.service.IPdFileService;

@WebMvcTest(PdFileController.class)
public class PdFileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "dbcoder-PdFileServiceImpl")
    private IPdFileService pdFileService;

    @Test
    void createUploadTest() throws Exception {

        BDDMockito.given(this.pdFileService.create(BDDMockito.any(PdFileBo.class))).willReturn(true);
        
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "1.pdm", null,
                new FileInputStream(new File("C:\\Users\\Think\\Desktop\\1.pdm")));

        MvcResult mvcResult = this.mvc
                .perform(MockMvcRequestBuilders.multipart("/pdfile")
                        .file(mockMultipartFile)
                        .param("basePackage", "io.github.ludongrong")
                        .param("projectName", "dbcoder")
                        .param("name", "springboot-2.4.0-jdbc")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(result);
    }
}