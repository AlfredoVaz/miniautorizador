package br.com.miniautorizador.service;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.model.Card;

import java.math.BigDecimal;

public interface CardService {
    Card create(CardDTO cardDTO);
    BigDecimal getBalanceByCardNumber(String cardNumber);
}
