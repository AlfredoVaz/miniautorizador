package br.com.miniautorizador.controller;

import br.com.miniautorizador.dto.CardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CardEndpointGetTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/cartoes";
    private static final String BASIC_AUTH = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    @Test
    @DisplayName("Deve retornar o saldo do cartão existente")
    void shouldReturnCardBalance() throws Exception {
        CardDTO dto = new CardDTO("1111222233334444", "1234");

        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
                
        mockMvc.perform(get(BASE_URL + "/1111222233334444")
                .header("Authorization", BASIC_AUTH))
                .andExpect(status().isOk())
                .andExpect(content().string("500.00"));
    }

    @Test
    @DisplayName("Deve retornar 404 para cartão inexistente")
    void shouldReturn404ForNonExistingCard() throws Exception {
        mockMvc.perform(get(BASE_URL + "/0000111122223333")
                .header("Authorization", BASIC_AUTH))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 401 sem autenticação")
    void shouldReturn401WithoutAuth() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1111222233334444"))
                .andExpect(status().isUnauthorized());
    }
}
