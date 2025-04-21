package com.newspaper.newspaper;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ArticleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public int createTestUserAndGetId() throws Exception {
        Map<String, String> user = new HashMap<>();
        user.put("name", "Juan");
        user.put("lastname", "Pérez");
        user.put("email", "juanperez@example.com");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseJson).get("id").asInt();
    }


    @Test
    void createArticle_shouldPersistArticleAndReturn201() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "image.jpg",
            "image/jpeg",
            "image".getBytes()  
        );

        int userId = createTestUserAndGetId();

        mockMvc.perform(multipart("/article/user/" + userId)
            .file(file)
            .param("title", "Título de artículo")
            .param("content", "Este es un contenido de prueba suficientemente largo para cumplir con la validación.")
            .param("category", "ART")
            .with(request -> { request.setMethod("POST"); return request; }))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Título de artículo"));
    }

    
}
