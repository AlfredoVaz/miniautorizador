package br.com.miniautorizador.service;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.model.Card;
import br.com.miniautorizador.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("500.00");

    @Autowired
    private CardRepository repository;

    @Override
    public Card create(CardDTO card) {
        Optional<Card> possibleCard = repository.findById(card.getCardNumber());
        if (possibleCard.isPresent()) {
            throw new RuntimeException("CARD_ALREADY_EXISTS");
        }
        Card newCard = buildCard(card);
        return repository.save(newCard);
    }

    private Card buildCard(CardDTO card) {
        Card newCard = new Card();
        newCard.setCardNumber(card.getCardNumber());
        newCard.setPassword(card.getPassword());
        newCard.setBalance(INITIAL_BALANCE);
        newCard.setHolderName("");
        newCard.setExpirationDate(LocalDate.now().plusYears(5));
        return newCard;
    }
}
