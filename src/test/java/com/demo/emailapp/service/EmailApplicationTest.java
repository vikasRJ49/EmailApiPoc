package com.demo.emailapp.service;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.emailapp.model.MessagePojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class EmailApplicationTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    ObjectMapper om = new ObjectMapper();


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();


    }

    @Test
    public void listEmailTest() throws Exception {

        String requestBody = "{\"username\":\"pieeyecandidate@outlook.com\",\"password\":\"2021-codder#\"}";
        MvcResult result = mockMvc
                .perform(post("/inbox/email/3").param("username", "pieeyecandidate@outlook.com")
                        .param("password", "2021-codder#").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        MessagePojo[] response = om.readValue(resultContent, MessagePojo[].class);
        Assert.assertTrue(response != null);


    }

    @Test
    public void listEmailsTest() throws Exception {

        String requestBody = "{\"username\":\"pieeyecandidate@outlook.com\",\"password\":\"2021-codder#\"}";
        MvcResult result = mockMvc
                .perform(post("/inbox/emails").param("username", "pieeyecandidate@outlook.com")
                        .param("password", "2021-codder#").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        MessagePojo[] response = om.readValue(resultContent, MessagePojo[].class);
        Assert.assertTrue(response != null);


    }


}
