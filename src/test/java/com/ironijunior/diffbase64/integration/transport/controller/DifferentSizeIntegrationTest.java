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

public class DifferentSizeIntegrationTest extends AbstractIntegrationTest {

    @Test
    @Order(1)
    public void sendingDifferentSizeDataToLeftSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_LEFT_DATA.getBytes())))
                .andExpect(content().string(StringUtils.EMPTY))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void sendingDifferentSizeDataToRightSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(RIGHT_API, ScenariosConstants.DIFFERENT_SIZE_ID))
                        .contentType(MediaType.ALL_VALUE)
                        .content(Base64.getEncoder().encode(ScenariosConstants.DIFFERENT_SIZE_RIGHT_DATA.getBytes())))
                .andExpect(content().string(StringUtils.EMPTY))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void gettingDiffDifferentSizeTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, ScenariosConstants.DIFFERENT_SIZE_ID)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\" : \"Objects are different in size\"}"))
                .andExpect(status().isOk());
    }
}
