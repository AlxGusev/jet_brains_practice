import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);

        Account account = null;

        boolean stayInAccount = true;
        boolean logIn = false;
        int num;

        while (stayInAccount) {

            if (!logIn) {

                System.out.println("1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");

                num = Integer.parseInt(scanner.nextLine());
                switch (num) {
                    case 1:
                        account = new Account();
                        break;
                    case 2:
                        logIn = logIntoAccount(account, scanner);
                        break;
                    case 0:
                        stayInAccount = false;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + num);
                }

            } else {

                System.out.println("1. Balance");
                System.out.println("2. Log out");
                System.out.println("0. Exit");

                num = Integer.parseInt(scanner.nextLine());

                switch (num) {
                    case 1:
                        account.getBalance();
                        break;
                    case 2:
                        logIn = false;
                        System.out.println("You have successfully logged out!");
                        break;
                    case 0:
                        stayInAccount = false;
                        break;
                }
            }
        }
        System.out.println("Bye");
        System.exit(0);
    }

    public static boolean logIntoAccount(Account account, Scanner scanner) {

        System.out.println("Enter your card number:");
        String number = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();

        if (account == null || !account.getCreditCard().getCardNumber().equals(number) ||
                !account.getCreditCard().getPinCode().equals(pin)) {
            System.out.println("Wrong card number or PIN!");
            return false;
        }
        System.out.println("You have successfully logged in!");
        return true;
    }

}
