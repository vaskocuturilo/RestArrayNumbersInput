package com.example.restarraynumbersinput;

import com.example.restarraynumbersinput.entity.NumberEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NumbersRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void givenNumbers_whenCreatedNumber_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/numbers")
                        .content(new ObjectMapper().writeValueAsString(List.of(new NumberEntity(100), new NumberEntity(200), new NumberEntity(300))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].number").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].number", hasItems(100, 200, 300)));
    }

    @Test
    public void givenNumbers_whenGetAllNumbers_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/numbers")
                .content(new ObjectMapper().writeValueAsString(List.of(new NumberEntity(100), new NumberEntity(200), new NumberEntity(300))))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/numbers/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    public void givenNumbers_whenOperationWithNumber_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/numbers/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(300)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasItem(1)));
    }

    @Test
    public void givenNumbers_whenCreatedNumber_thenStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/numbers")
                        .content(new ObjectMapper().writeValueAsString(List.of(new NumberEntity(null))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("Not correctly format"));
    }

    @Test
    public void givenNumbers_whenDeleteNumber_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/numbers/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isEmpty())
                .andExpect(MockMvcResultMatchers.content().string("All numbers was delete."));
    }

    @Test
    public void givenNumbers_whenOperationsNumber_thenStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/numbers/operations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isEmpty())
                .andExpect(MockMvcResultMatchers.content().string("The list of numbers is Empty. Add List of numbers before, please."));
    }
}
