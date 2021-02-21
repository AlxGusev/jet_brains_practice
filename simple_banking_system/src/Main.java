import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static String url = "jdbc:sqlite:";

    public static void main(String[] args) {

        createDB(args[1]);

        final Scanner scanner = new Scanner(System.in);

        Account account = null;

        while (true) {

            printLogInMenu();

            int pick1 = Integer.parseInt(scanner.nextLine());

            switch (pick1) {
                case 1:
                    account = new Account();
                    addCardToDB(account);
                    break;
                case 2:
                    logIntoAccount(account, scanner);
                    break;
                case 0:
                    printExit();
                    System.exit(0);
                    break;
            }
        }
    }

    public static void addCardToDB(Account account) {

        String sql = "INSERT INTO card (number, pin, balance) VALUES (" +
                account.getCreditCard().getCardNumber() + ", " +
                account.getCreditCard().getPinCode() + ", " +
                account.getBalance() + ")";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {

            System.out.println("Add card to the table");

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDB(String fileName) {

        url += fileName;

        String sql = "CREATE TABLE IF NOT EXISTS card (" +
                "id INTEGER PRIMARY KEY, " +
                "number TEXT, " +
                "pin TEXT, " +
                "balance INTEGER DEFAULT 0)";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {

            System.out.println("Connected to DB" + fileName);

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logIntoAccount(Account account, Scanner scanner) {

        System.out.println("Enter your card number:");
        String number = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();

        if (account == null || !account.getCreditCard().getCardNumber().matches(number) ||
                !account.getCreditCard().getPinCode().matches(pin)) {
            System.out.println("Wrong card number or PIN!");
            return;
        }
        System.out.println("You have successfully logged in!");

        boolean logOut = false;
        while (!logOut) {

            printAccountMenu();

            int pick2 = Integer.parseInt(scanner.nextLine());

            switch (pick2) {
                case 1:
                    account.getBalance();
                    break;
                case 2:
                    logOut = true;
                    break;
                case 0:
                    printExit();
                    System.exit(0);
                    break;
            }
        }
    }

    private static void printAccountMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }

    private static void printLogInMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    private static void printExit() {
        System.out.println("Bye");
    }
}