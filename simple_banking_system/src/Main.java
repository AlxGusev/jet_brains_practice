import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    public static String url = "jdbc:sqlite:";

    public static void main(String[] args) {

        createDB(args[1]);


        Account account;

        while (true) {

            printLogInMenu();

            int pick1 = Integer.parseInt(scanner.nextLine());

            switch (pick1) {
                case 1:
                    account = new Account();
                    addCardToDB(account);
                    break;
                case 2:
                    logIntoAccount();
                    break;
                case 0:
                    printExit();
                    System.exit(0);
                    break;
            }
        }
    }

    public static void addCardToDB(Account account) {

        String sql = "INSERT INTO card (number, pin) VALUES (" +
                account.getCreditCard().getCardNumber() + ", " +
                account.getCreditCard().getPinCode() + ")";

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

    public static void logIntoAccount() {

        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();

//        if (account == null || !account.getCreditCard().getCardNumber().matches(number) ||
//            !account.getCreditCard().getPinCode().matches(pin)) {
//            System.out.println("Wrong card number or PIN!");
//            return;
//        }

        String sql = "SELECT number FROM card WHERE number = ? AND pin = ?";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, cardNumber);
            statement.setString(2, pin);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Wrong card number or PIN!");
                return;
            }

            System.out.println("You have successfully logged in!");

        } catch (SQLException e) {
            e.printStackTrace();
        }



        boolean logOut = false;
        while (!logOut) {

            printAccountMenu();

            int pick2 = Integer.parseInt(scanner.nextLine());

            switch (pick2) {
                case 1:
                    showAccountBalance(cardNumber);
                    break;
                case 2:
                    addIncome(cardNumber);
                    break;
                case 3:
                    doTransfer(cardNumber);
                    break;
                case 4:
                    closeAccount(cardNumber);
                    logOut = true;
                    break;
                case 5:
                    logOut = true;
                    break;
                case 0:
                    printExit();
                    System.exit(0);
                    break;
            }
        }
    }

    public static void doTransfer(String fromCardNumber) {

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String toCardNumber = scanner.nextLine();

        if (fromCardNumber.equals(toCardNumber)) {
            System.out.println("You can't transfer money to the same account!");
            return;
        } else if (!checkLuhnAlgorithm(toCardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        } else if (!ifCardNumberExists(toCardNumber)) {
            System.out.println("Such a card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int transferAmount = Integer.parseInt(scanner.nextLine());

        if (showAccountBalance(fromCardNumber) < transferAmount) {
            System.out.println("Not enough money!");
            return;
        }

        String withDrawSQL = "UPDATE card SET balance = balance - ? WHERE number = ?";
        String topUpSQL = "UPDATE card SET balance = balance + ? WHERE number = ?";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement withDraw = con.prepareStatement(withDrawSQL);
                 PreparedStatement topUp = con.prepareStatement(topUpSQL)) {


                withDraw.setInt(1, transferAmount);
                withDraw.setString(2, fromCardNumber);
                withDraw.executeUpdate();

                topUp.setInt(1, transferAmount);
                topUp.setString(2, toCardNumber);
                topUp.executeUpdate();

                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Success!");

    }

    public static boolean ifCardNumberExists(String cardNumber) {

        String sql = "SELECT number FROM card WHERE number = ?";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean checkLuhnAlgorithm(String cardNumber) {

        StringBuilder num = new StringBuilder(cardNumber);

        int sum = luhnAlgorithm(num.substring(0, 15));

        int lastDigit = num.charAt(15) - 48;

        return (sum + lastDigit) % 10 == 0;
    }

    public static void closeAccount(String cardNumber) {

        String sql = "DELETE FROM card WHERE number = ?";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, cardNumber);
            statement.executeUpdate();

            System.out.println("The account has been closed!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addIncome(String cardNumber) {

        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            System.out.println("Enter income:");
            int income = Integer.parseInt(scanner.nextLine());

            statement.setInt(1, income);
            statement.setString(2, cardNumber);
            statement.executeUpdate();

            System.out.println("Income was added!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int showAccountBalance(String cardNumber) {

        String sql = "SELECT balance FROM card WHERE number = ?";
        int balance = 0;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();

            balance = resultSet.getInt("balance");
            System.out.println("\nBalance: " + balance);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public static int luhnAlgorithm(String cardNumber) {

        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int n = cardNumber.charAt(i) - 48;
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
        }
        return sum;
    }

    private static void printAccountMenu() {
        System.out.println("\n1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
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