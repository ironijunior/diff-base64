package com.ironijunior.diffbase64.integration.transport.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EqualDataIntegrationTest extends AbstractIntegrationTest {

    @Test
    @Order(1)
    public void sendingEqualDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(content().string(StringUtils.EMPTY))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingEqualDataToRightSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(RIGHT_API, ScenariosConstants.EQUAL_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(content().string(StringUtils.EMPTY))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void gettingDiffEqualTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, ScenariosConstants.EQUAL_ID)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\" : \"Objects are equals\"}"))
                .andExpect(status().isOk());
    }
}
