package com.moplus.moplus_server.domain.auth.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Nested
    class 어드민_로그인 {

        @Test
        void 성공() throws Exception {

            AdminLoginRequest request = new AdminLoginRequest("admin@example.com", "password123");
            String requestBody = objectMapper.writeValueAsString(request);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isOk()) // HTTP 200 응답 확인
                    .andExpect(jsonPath("$.accessToken").isNotEmpty()) // accessToken 필드 존재 여부 확인
                    .andExpect(cookie().exists("refreshToken")) // 리프레시 토큰 쿠키 존재 확인
                    .andExpect(cookie().httpOnly("refreshToken", true)) // HTTP Only 설정 확인
                    .andExpect(cookie().secure("refreshToken", true)) // Secure 설정 확인
                    .andExpect(cookie().path("refreshToken", "/")) // 쿠키 경로 확인
                    .andExpect(cookie().attribute("refreshToken", "SameSite", "None"));
        }


        @Test
        void 잘못된_요청_본문() throws Exception {

            record TempRecord(String data) {
            }

            TempRecord request = new TempRecord("임시 테스트 요청 본문");
            String requestBody = objectMapper.writeValueAsString(request);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isUnauthorized());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "plainaddress",              // 이메일 형식이 아님
                "@missingusername.com",      // 사용자명 없음
                "username@.com",             // 도메인 이름 없음
                "username@com",              // 잘못된 도메인
                "username@domain..com",      // 연속된 점
                "username@domain,com",       // 쉼표 포함
                "username@domain space.com", // 공백 포함
                "username@domain.com space", // 공백 포함
                "username@domain#com",       // 특수문자 포함
                ""                           // 빈 문자열
        })
        void 잘못된_이메일_양식(String email) throws Exception {

            AdminLoginRequest request = new AdminLoginRequest(email, "password123");
            String requestBody = objectMapper.writeValueAsString(request);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isUnauthorized()); // 401 응답 확인
        }

        @Test
        void 실패() throws Exception {

            AdminLoginRequest request = new AdminLoginRequest("admin@example.com", "wrong123");
            String requestBody = objectMapper.writeValueAsString(request);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/admin/login")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isUnauthorized()); // 401 응답 확인
        }
    }
}