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

        StringBuilder number = new StringBuilder(String.format("400000%09d", ThreadLocalRandom.current().nextInt(1000000000)));

        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int n = number.charAt(i) - 48;
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
        }

        int lastDigit = (10 - sum % 10) % 10;
        number.append(lastDigit);

        return number.toString();
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
