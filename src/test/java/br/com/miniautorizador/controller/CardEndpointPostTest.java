package br.com.miniautorizador.controller;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.exception.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CardEndpointPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/cards";
    private static final String BASIC_AUTH = "Basic dXNlcm5hbWU6cGFzc3dvcmQ="; 
    
    @Test
    @DisplayName("Deve criar cartão com sucesso")
    void shouldCreateCardSuccessfully() throws Exception {
        CardDTO dto = new CardDTO("1234567890123456", "1234");
        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123456"));
    }

    @Test
    @DisplayName("Deve retornar 422 para cardNumber vazio")
    void shouldReturn422ForEmptyCardNumber() throws Exception {
        CardDTO dto = new CardDTO("", "1234");
        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.cardNumber").value(Messages.CARD_NUMBER_REQUIRED));
    }

    @Test
    @DisplayName("Deve retornar 422 para cardNumber inválido")
    void shouldReturn422ForInvalidCardNumber() throws Exception {
        CardDTO dto = new CardDTO("123", "1234");
        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.cardNumber").value(Messages.CARD_NUMBER_INVALID));
    }

    @Test
    @DisplayName("Deve retornar 422 para password vazio")
    void shouldReturn422ForEmptyPassword() throws Exception {
        CardDTO dto = new CardDTO("1234567890123456", "");
        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.password").value(Messages.PASSWORD_REQUIRED));
    }

    @Test
    @DisplayName("Deve retornar 422 para password inválido")
    void shouldReturn422ForInvalidPassword() throws Exception {
        CardDTO dto = new CardDTO("1234567890123456", "12");
        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.password").value(Messages.PASSWORD_INVALID));
    }

    @Test
    @DisplayName("Deve retornar 422 para cartão já existente")
    void shouldReturn422ForExistingCard() throws Exception {
        CardDTO dto = new CardDTO("9999999999999999", "1234");

        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(BASE_URL)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value(Messages.CARD_ALREADY_EXISTS));
    }

    @Test
    @DisplayName("Deve retornar 401 sem autenticação")
    void shouldReturn401WithoutAuth() throws Exception {
        CardDTO dto = new CardDTO("1234567890123456", "1234");
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }
}
