package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.ExercisesBackApplication;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.enumeration.Status;
import com.arturfrimu.exercisesback.service.GenerateExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ExercisesBackApplication.class)
class ExerciseGeneratorControllerV7IntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenerateExerciseService generateExerciseService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser
    void testGenerateExerciseV1() throws Exception {
        ExerciseResponse mockResponse = new ExerciseResponse(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "1 + 1 = ?",
                Status.CORRECT
        );

        when(generateExerciseService.generate()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v7/exercise-generator").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expression").value(mockResponse.expression()))
                .andExpect(jsonPath("$.status").value(mockResponse.status().toString()))
                .andExpect(jsonPath("$.id").value(mockResponse.id().toString()));
    }

    @Test
    @WithMockUser
    void testGenerateExerciseV2() throws Exception {
        ExerciseResponse mockResponse = new ExerciseResponse(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "1 + 1 = ?",
                Status.CORRECT
        );

        when(generateExerciseService.generate()).thenReturn(mockResponse);

        String expectedJson = objectMapper.writeValueAsString(mockResponse);

        mockMvc.perform(get("/api/v7/exercise-generator").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
