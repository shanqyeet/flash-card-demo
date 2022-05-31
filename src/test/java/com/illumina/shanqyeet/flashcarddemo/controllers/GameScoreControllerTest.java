package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.illumina.shanqyeet.flashcarddemo.security.JwtAuthenticationEntryPoint;
import com.illumina.shanqyeet.flashcarddemo.security.JwtTokenProvider;
import com.illumina.shanqyeet.flashcarddemo.services.gamescorehistory.GetTopHundredScoresService;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.users.UserDetailsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameScoreController.class)
@AutoConfigureMockMvc
class GameScoreControllerTest {
    @MockBean
    private GetTopHundredScoresService getTopHundredScoresServiceMock;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private UserDetailsValidator userDetailsValidator;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTopHundredScoresSuccess() throws Exception {
        mockMvc.perform(get("/scores/top-100-leaders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }
}