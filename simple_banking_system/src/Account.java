
public class Account {

    private final CreditCard creditCard;
    private long balance;

    Account() {
        this.creditCard = createCreditCard();
        balance = 0;
    }

    private CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard();
        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" + creditCard.getCardNumber());
        System.out.println("Your card PIN:\n" + creditCard.getPinCode());
        return creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void getBalance() {
        System.out.println("Balance: " + balance);
    }

}
