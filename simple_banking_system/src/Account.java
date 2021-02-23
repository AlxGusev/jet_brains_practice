public class Account {

    private final CreditCard creditCard;
    private int balance;

    Account() {

        this.creditCard = createCreditCard();
    }

    private CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard();
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:\n" + creditCard.getCardNumber());
        System.out.println("Your card PIN:\n" + creditCard.getPinCode() + "\n");

        return creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int income) {
        this.balance += income;
    }
}


