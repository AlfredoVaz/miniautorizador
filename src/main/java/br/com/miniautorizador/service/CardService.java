package br.com.miniautorizador.service;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.model.Card;

public interface CardService {
    Card create(CardDTO cardDTO);
}
