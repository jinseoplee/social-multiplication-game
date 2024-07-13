package com.ljs.smg.multiplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MultiplicationController.class)
class MultiplicationControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MultiplicationResponse> json;
    private JacksonTester<MultiplicationAttemptRequest> jsonAttemptRequest;
    private JacksonTester<MultiplicationAttemptResponse> jsonAttemptResponse;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getRandomMultiplicationTest() throws Exception {
        // given
        given(multiplicationService.createRandomMultiplication())
                .willReturn(new MultiplicationResponse(2, 5));

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/multiplication/random")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(json.write(new MultiplicationResponse(2, 5)).getJson());
    }

    @Test
    public void checkCorrectAttemptTest() throws Exception {
        checkAttempt("ljs", 2, 5, 10, true);
    }

    @Test
    public void checkIncorrectAttemptTest() throws Exception {
        checkAttempt("ljs", 7, 9, 36, false);
    }

    private void checkAttempt(String userId, int factorA, int factorB, int answer, boolean isCorrect) throws Exception {
        // given
        MultiplicationAttemptRequest request = new MultiplicationAttemptRequest(userId, factorA, factorB, answer);
        MultiplicationAttemptResponse response = new MultiplicationAttemptResponse(isCorrect);

        given(multiplicationService.checkAttempt(any(MultiplicationAttemptRequest.class)))
                .willReturn(response);

        // when, then
        mvc.perform(post("/api/v1/multiplication/attempt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAttemptRequest.write(request).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAttemptResponse.write(response).getJson()));
    }

    @Test
    public void invalidAttemptRequestTest() throws Exception {
        // given
        MultiplicationAttemptRequest request = new MultiplicationAttemptRequest("", 2, 8, 0);

        // when, then
        mvc.perform(post("/api/v1/multiplication/attempt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAttemptRequest.write(request).getJson()))
                .andExpect(status().isBadRequest());
    }
}
