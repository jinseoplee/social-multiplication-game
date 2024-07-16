package com.ljs.smg.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljs.smg.badge.Badge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameStatisticsController.class)
class GameStatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    private JacksonTester<GameStatistics> jsonGameStatistics;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getStatisticsTest() throws Exception {
        // given
        String userId = "ljs";
        int totalScore = 100;
        List<Badge> badges = List.of(Badge.FIRST_WON, Badge.BRONZE);
        GameStatistics gameStatistics = new GameStatistics(userId, totalScore, badges);

        given(gameService.getUserStatistics(userId))
                .willReturn(gameStatistics);

        // when & then
        mvc.perform(get("/api/v1/gamification/statistics")
                        .param("userId", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGameStatistics.write(gameStatistics).getJson()));
    }
}