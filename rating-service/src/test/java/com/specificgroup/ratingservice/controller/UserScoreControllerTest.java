package com.specificgroup.ratingservice.controller;

import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.service.UserScoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserScoreController.class)
class UserScoreControllerTest {

    @MockBean
    private UserScoreService userScoreService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<List<ScoreRow>> jsonList;
    @Autowired
    private JacksonTester<ScoreRow> json;


    @Test
    @DisplayName("The leaderboard should show the top 2")
    public void getLeaderBoardTest() throws Exception {
        // given
        ScoreRow leaderBoardRow1 = new ScoreRow(UUID.randomUUID(), "username1",120L);
        ScoreRow leaderBoardRow2 = new ScoreRow(UUID.randomUUID(), "username1", 100L);
        List<ScoreRow> leaderBoard = new ArrayList<>();
        Collections.addAll(leaderBoard, leaderBoardRow1, leaderBoardRow2);
        given(userScoreService.getCurrentLeaderBoard(anyInt())).willReturn(leaderBoard);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/score/leaders")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("top", "2"))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(jsonList.write(leaderBoard).getJson());
    }

    @Test
    @DisplayName("Should receive a user score")
    public void getUserScoreTest() throws Exception {
        // given
        ScoreRow leaderBoardRow = new ScoreRow(UUID.randomUUID(), "username",10L);
        given(userScoreService.getCurrentUserScore(any(UUID.class))).willReturn(leaderBoardRow);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/score")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("id", UUID.randomUUID().toString()))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(json.write(leaderBoardRow).getJson());
    }
}