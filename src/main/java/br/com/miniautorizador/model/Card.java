package br.com.miniautorizador.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CARD")
public class Card {
    @Id
    @Column(name = "CARD_NUMBER", unique = true, nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @Column(name = "HOLDER_NAME", nullable = false)
    private String holderName;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDate expirationDate;

    public Card() {}

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
}
