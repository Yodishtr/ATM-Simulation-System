package DataTransferObjects;

public class CardCreatedDTO {

    private String cardNumber;
    private String userName;
    private String firstName;
    private String lastName;

    public CardCreatedDTO(String cardNumber, String userName, String firstName, String lastName) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
