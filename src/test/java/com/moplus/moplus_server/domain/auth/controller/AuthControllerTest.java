package com.moplus.moplus_server.domain.auth.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2test")
@Sql({"/auth-test-data.sql"})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @Test
    void 어드민_로그인_성공() throws Exception {

        AdminLoginRequest request = new AdminLoginRequest("admin@example.com", "password123"); // DTO 객체 생성
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk()) // 200 응답 확인
                .andExpect(header().exists("Authorization"))
                .andExpect(header().exists("RefreshToken"));

    }

    @Test
    void 어드민_로그인_실패() throws Exception {

        AdminLoginRequest request = new AdminLoginRequest("admin@example.com", "wrong123"); // DTO 객체 생성
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isUnauthorized()); // 401 응답 확인
    }
}