package com.github.ricbau.otp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricbau.otp.output.OtpOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OtpControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginViaOtp() throws Exception {
        String response = mockMvc.perform(post("/v1/otp/mail/foo@bar.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", notNullValue()))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        OtpOutput otpOutput = objectMapper.readValue(response, OtpOutput.class);

        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "username": "foo@bar.com",
                                    "otp": "%s"
                                }
                                """, otpOutput.code()))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andDo(print());
    }

    @Test
    void shouldFailOnWrongOtp() throws Exception {
        mockMvc.perform(post("/v1/otp/mail/foo@bar.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", notNullValue()))
                .andDo(print());

        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "username": "foo@bar.com",
                                    "otp": "%s"
                                }
                                """, "1234"))
                ).andExpect(status().isConflict())
                .andDo(print());
    }
}