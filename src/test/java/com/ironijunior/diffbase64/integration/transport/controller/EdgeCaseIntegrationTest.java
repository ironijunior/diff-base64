package com.ironijunior.diffbase64.integration.transport.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EdgeCaseIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void gettingDiffOfNonExistentIdTest() throws Exception {
        mockMvc.perform(
                get(String.format(DIFF_API, "xxx")))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"There is no data with the identifier xxx.\"}", true));
    }

    @Test
    public void sendingAlreadyExistentSideTest() throws Exception {
        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.ALREADY_EXIST_SIDE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())));

        mockMvc.perform(
                post(String.format(LEFT_API, ScenariosConstants.ALREADY_EXIST_SIDE_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Base64.getEncoder().encode(ScenariosConstants.EQUAL_DATA.getBytes())))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"The side LEFT is already filled.\"}", true));
    }

    @Test
    public void gettingNotFoundOnInvalidUrl() throws Exception {
        mockMvc.perform(
                get("/non-existent/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void gettingMethodNotAllowedTest() throws Exception {
        mockMvc.perform(
                get(String.format(LEFT_API, "any")))
                .andExpect(status().isMethodNotAllowed());
    }
}
