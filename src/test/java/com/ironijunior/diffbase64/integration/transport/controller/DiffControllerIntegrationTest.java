package com.ironijunior.diffbase64.integration.transport.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DiffControllerIntegrationTest {

    private static final String RIGHT_API = "/v1/diff/%s/right";
    private static final String LEFT_API = "/v1/diff/%s/left";
    private static final String DIFF_API = "/v1/diff/%s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void sendingEqualDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                    post(String.format(LEFT_API, ScenariosConstants.EQUAL_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingEqualDataToRightSideTest() throws Exception {
        mockMvc.perform(
                    post(String.format(RIGHT_API, ScenariosConstants.EQUAL_ID))
                    .contentType(MediaType.ALL_VALUE)
                    .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void gettingDiffEqualTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, ScenariosConstants.EQUAL_ID)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\" : \"Objects are equals\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void sendingDifferentSizeDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_LEFT_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    public void sendingDifferentSizeDataToRightSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(RIGHT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_RIGHT_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(6)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void gettingDiffDifferentSizeTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, ScenariosConstants.DIFFERENT_SIZE_ID)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\" : \"Objects are different in size\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void sendingDifferentDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.DIFFERENT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_LEFT_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(8)
    public void sendingDifferentDataToRightSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(RIGHT_API, ScenariosConstants.DIFFERENT_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_RIGHT_DATA.getBytes())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(9)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void gettingDiffDifferentDataTest() throws Exception {
        String expectedJson = "{" +
                "\"status\":\"Objects are different\"" +
                ",\"differences\":[" +
                "{\"initialOffset\":8,\"finalOffset\":13,\"length\":5}," +
                "{\"initialOffset\":14,\"finalOffset\":16,\"length\":2}," +
                "{\"initialOffset\":18,\"finalOffset\":20,\"length\":2}," +
                "{\"initialOffset\":27,\"finalOffset\":46,\"length\":19}]}";
        mockMvc.perform(
                get(String.format(DIFF_API, ScenariosConstants.DIFFERENT_ID)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson, true))
                .andExpect(status().isOk());
    }

    @Order(10)
    @Test
    public void gettingDiffOfNonExistentIdTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, "xxx")))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"There is no data with the identifier xxx.\"}", true));
    }

    @Order(11)
    @Test
    public void sendingAlreadyExistentSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())));

        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"The side LEFT is already filled.\"}", true));
    }
}
