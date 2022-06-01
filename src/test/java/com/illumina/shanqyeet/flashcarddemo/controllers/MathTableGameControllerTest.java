package com.illumina.shanqyeet.flashcarddemo.controllers;

import com.google.gson.Gson;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeRequest;
import com.illumina.shanqyeet.flashcarddemo.dtos.requests.PostChallengeResultRequest;
import com.illumina.shanqyeet.flashcarddemo.security.JwtAuthenticationEntryPoint;
import com.illumina.shanqyeet.flashcarddemo.security.JwtTokenProvider;
import com.illumina.shanqyeet.flashcarddemo.services.helpers.users.UserDetailsValidator;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.GetValidateOnGoingGameService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostChallengeResultService;
import com.illumina.shanqyeet.flashcarddemo.services.mathtablegame.PostCompleteGameService;
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

import static com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty.MathTableGame.EASY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MathTableGameController.class)
@AutoConfigureMockMvc
class MathTableGameControllerTest {

    @MockBean
    private PostChallengeService getChallengeServiceMock;

    @MockBean
    private GetValidateOnGoingGameService getValidateOnGoingGameServiceMock;

    @MockBean
    private PostCompleteGameService postCompleteGameServiceMock;

    @MockBean
    private PostChallengeResultService postChallengeResultServiceMock;

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
    Gson gson = new Gson();

    @Test
    public void validateOnGoingGameSuccess() throws Exception {
        mockMvc.perform(post("/math/challenge/check-session")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void retrieveNextChallengeSuccess() throws Exception {
       PostChallengeRequest request = PostChallengeRequest.builder()
               .isNewGame(true)
               .gameDifficulty(EASY)
               .build();

        mockMvc.perform(post("/math/challenge/new/test")
                       .content(gson.toJson(request))
                       .contentType(MediaType.APPLICATION_JSON)
               ).andExpect(status().isOk());
    }

    @Test
    void updateChallengeResultSuccess() throws Exception {
        PostChallengeResultRequest request = PostChallengeResultRequest.builder()
                .answerTimeInMillis(Long.valueOf(10000))
                .isPassed(true)
                .build();

        mockMvc.perform(post("/math/challenge/result")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void completeChallengeSuccess() throws Exception {
        mockMvc.perform(put("/math/challenge/complete")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}