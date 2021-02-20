import java.util.concurrent.ThreadLocalRandom;

public class CreditCard {

    private final String cardNumber;
    private final String pinCode;

    CreditCard() {
        this.cardNumber = setCardNumber();
        this.pinCode = setPinCode();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    private String setCardNumber() {
        return String.format("400000%05d%05d", ThreadLocalRandom.current().nextInt(100000), ThreadLocalRandom.current().nextInt(100000));
    }

    public String getPinCode() {
        return pinCode;
    }

    private String setPinCode() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
