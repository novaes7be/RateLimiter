package com.contatodireto.ratelimiterproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RateLimiterIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void insideLimit() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk());
    }

    @Test
    public void exceedLimits() throws Exception {


        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/hello")).andExpect(status().isOk());
        }
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isTooManyRequests())
                .andExpect(header().string("Retry-After", "1"));
    }


}
