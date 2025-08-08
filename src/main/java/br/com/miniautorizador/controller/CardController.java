package br.com.miniautorizador.controller;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.model.Card;
import br.com.miniautorizador.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardDTO> create(@Valid @RequestBody CardDTO cardDTO) {
        Card card = cardService.create(cardDTO);
        CardDTO response = new CardDTO(card.getCardNumber(), card.getPassword());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
