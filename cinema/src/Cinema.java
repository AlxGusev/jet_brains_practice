import java.util.Scanner;

public class Cinema {

    private static String[][] cinemaHall;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int HIGH_PRICE = 10;
    private static final int LOW_PRICE = 8;

    private static int totalIncome;
    private static int currentIncome;
    private static int numberOfPurchased;

    private static int numberOfRows;
    private static int numberOfSeats;
    private static int totalNumberOfSeats;

    public static void main(String[] args) {

        createCinemaHall();

        int pick;
        boolean flag = true;

        while (flag) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            pick = SCANNER.nextInt();

            switch (pick) {
                case 1:
                    showCinemaHall();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Error! Wrong command!");
                    break;
            }
        }
    }

    public static void createCinemaHall() {

        System.out.println("Enter the number of rows:");
        numberOfRows = SCANNER.nextInt();
        System.out.println("Enter the number of seats in each row:");
        numberOfSeats = SCANNER.nextInt();
        cinemaHall = new String[numberOfRows][numberOfSeats];

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfSeats; j++) {
                cinemaHall[i][j] = "S";
            }
        }

        totalNumberOfSeats = numberOfRows * numberOfSeats;

        if (totalNumberOfSeats <= 60) {
            totalIncome = totalNumberOfSeats * HIGH_PRICE;
        } else {
            int expensiveRows = numberOfRows / 2;
            int totalExpensiveTicketsPrice = expensiveRows * numberOfSeats * HIGH_PRICE;
            int totalCheapTicketsPrice = (numberOfRows - expensiveRows) * numberOfSeats * LOW_PRICE;
            totalIncome = totalExpensiveTicketsPrice + totalCheapTicketsPrice;
        }
    }

    public static void showCinemaHall() {

        System.out.println("Cinema:");
        System.out.print(" ");

        for (int i = 1; i <= numberOfSeats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < numberOfRows; i++) {
            System.out.print(i + 1);
            for (int m = 0; m < numberOfSeats; m++) {
                System.out.print(" " + cinemaHall[i][m]);
            }
            System.out.println();
        }
    }

    public static void buyTicket() {

        int seatRow;
        int seatNumber;

        while (true) {
            System.out.println("Enter a row number:");
            seatRow = SCANNER.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumber = SCANNER.nextInt();

            if (seatRow > numberOfRows || seatNumber > numberOfSeats || seatRow <= 0 || seatNumber <= 0) {
                System.out.println("Wrong input!");
            } else {
                if (cinemaHall[seatRow - 1][seatNumber - 1].equals("B")) {
                    System.out.println("That ticket has already been purchased!");
                } else {
                    break;
                }
            }
        }

        int priceTicket;

        if (totalNumberOfSeats <= 60) {
            priceTicket = HIGH_PRICE;
        } else {
            int frontHalfOfRows = cinemaHall.length / 2;
            if (seatRow <= frontHalfOfRows) {
                priceTicket = HIGH_PRICE;
            } else {
                priceTicket = LOW_PRICE;
            }
        }

        currentIncome += priceTicket;
        numberOfPurchased++;

        System.out.println("Ticket price: $" + priceTicket);
        cinemaHall[seatRow - 1][seatNumber - 1] = "B";
    }

    public static void showStatistics() {

        float PercentageOfOccupancy = (float) 100 / totalNumberOfSeats * numberOfPurchased;

        System.out.println("Number of purchased tickets: " + numberOfPurchased);
        System.out.printf("Percentage: %.2f%s%n", PercentageOfOccupancy, "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}
