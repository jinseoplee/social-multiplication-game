package com.ljs.smg.leaderboard;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(LeaderboardController.class)
class LeaderboardControllerTest {

    @MockBean
    private LeaderboardService leaderboardService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<List<LeaderboardRow>> jsonLeaderboard;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getLeaderboardTest() throws Exception {
        // given
        List<LeaderboardRow> leaderboard = List.of(
                new LeaderboardRow("user 1", 100),
                new LeaderboardRow("user 2", 120),
                new LeaderboardRow("user 3", 20)
        );

        given(leaderboardService.getLeaderboard())
                .willReturn(leaderboard);

        // when
        mvc.perform(get("/api/v1/gamification/leaderboard")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLeaderboard.write(leaderboard).getJson()));
    }
}