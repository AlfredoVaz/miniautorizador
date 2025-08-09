package br.com.miniautorizador.service;

import br.com.miniautorizador.dto.CardDTO;
import br.com.miniautorizador.model.Card;
import br.com.miniautorizador.repository.CardRepository;
import br.com.miniautorizador.exception.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Value("${card.initial-balance}")
    private BigDecimal initialBalance;

    @Autowired
    private CardRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Card create(CardDTO card) {
        Optional<Card> possibleCard = repository.findByCardNumber(card.getCardNumber());
        if (possibleCard.isPresent()) {
            throw new RuntimeException(Messages.CARD_ALREADY_EXISTS);
        }
        Card newCard = buildCard(card);
        return repository.save(newCard);
    }

    @Override
    public BigDecimal getBalanceByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber)
                .map(Card::getBalance)
                .orElseThrow(() -> new RuntimeException(Messages.CARD_NOT_FOUND));
    }

    private Card buildCard(CardDTO card) {
        Card newCard = new Card();
        newCard.setCardNumber(card.getCardNumber());
        newCard.setPassword(passwordEncoder.encode(card.getPassword()));
        newCard.setBalance(initialBalance);
        newCard.setHolderName("");
        newCard.setExpirationDate(LocalDate.now().plusYears(5));
        return newCard;
    }
}
