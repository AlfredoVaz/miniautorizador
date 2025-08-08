package br.com.miniautorizador.dto;

public class CardDTO {
    private String cardNumber;
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
