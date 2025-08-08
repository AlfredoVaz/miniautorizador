package br.com.miniautorizador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import br.com.miniautorizador.exception.Messages;

public class CardDTO {
    
    @NotBlank(message = Messages.CARD_NUMBER_REQUIRED)
    @Pattern(regexp = "^[0-9]{16}$", message = Messages.CARD_NUMBER_INVALID)
    private String cardNumber;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Pattern(regexp = "^[0-9]{4}$", message = Messages.PASSWORD_INVALID)
    private String password;

    public CardDTO() {}

    public CardDTO(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
