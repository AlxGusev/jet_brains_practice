public class Account {

    private final CreditCard creditCard;
    private long balance = 0;
    private int accountId;

    Account() {
        this.creditCard = createCreditCard();
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

    public long getBalance() {
        System.out.println("Balance: " + balance);
        return balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int id) {
        this.accountId = id;
    }
}


