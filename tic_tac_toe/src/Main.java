import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final char[][] chars = new char[3][3];
    private static int x;
    private static int y;
    private static String turn = "Player_1";
    private static boolean hasMatch = false;
    private static boolean hasWinner = false;
    private static int countTurns = 0;


    public static void main(String[] args) {

        createBoard();

        while (!hasWinner) {

            switch (turn) {

                case "Player_1":
                    validatingInput();
                    checkingCoordinates();
                    if (hasMatch) {
                        validatingInput();
                        checkingCoordinates();
                    }
                    showBoard();
                    checkingGameStatus();
                    turn = "Player_2";
                    break;
                case "Player_2":
                    validatingInput();
                    checkingCoordinates();
                    if (hasMatch) {
                        validatingInput();
                        checkingCoordinates();
                    }
                    showBoard();
                    checkingGameStatus();
                    turn = "Player_1";
                    break;
            }
        }
        System.exit(0);
    }

    public static void createBoard() {

        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                chars[i][j] = ' ';
                System.out.print(chars[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void showBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(chars[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void validatingInput() {

        boolean check = false;

        while (!check) {

            System.out.print("Enter the coordinates: ");

            String[] inputLine = scanner.nextLine().split(" ");

            try {
                x = Integer.parseInt(inputLine[0]);
                y = Integer.parseInt(inputLine[1]);
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (x < 0 || x > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (y < 0 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            check = true;
        }

    }

    public static void checkingCoordinates() {

        hasMatch = false;

        if (chars[x - 1][y - 1] == ' ' && "Player_1".equals(turn)) {
            chars[x - 1][y - 1] = 'X';
            countTurns++;
        } else if (chars[x - 1][y - 1] == ' ' && "Player_2".equals(turn)) {
            chars[x - 1][y - 1] = 'O';
            countTurns++;
        } else if (chars[x - 1][y - 1] == 'X' || chars[x - 1][y - 1] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            hasMatch = true;
        }
    }

    private static void checkingGameStatus() {

        char winnerSign = 0;

        if (chars[0][0] == chars[0][1] && chars[0][1] == chars[0][2]) {
            if (chars[0][0] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][0] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[1][0] == chars[1][1] && chars[1][1] == chars[1][2]) {
            if (chars[1][0] == 'X') {
                winnerSign = 'X';
            } else if (chars[1][0] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[2][0] == chars[2][1] && chars[2][1] == chars[2][2]) {
            if (chars[2][0] == 'X') {
                winnerSign = 'X';
            } else if (chars[2][0] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[0][0] == chars[1][0] && chars[1][0] == chars[2][0]) {
            if (chars[0][0] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][0] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[0][1] == chars[1][1] && chars[1][1] == chars[2][1]) {
            if (chars[0][1] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][1] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[0][2] == chars[1][2] && chars[1][2] == chars[2][2]) {
            if (chars[0][2] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][2] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[0][0] == chars[1][1] && chars[1][1] == chars[2][2]) {
            if (chars[0][0] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][0] == 'O') {
                winnerSign = 'O';
            }
        }
        if (chars[0][2] == chars[1][1] && chars[1][1] == chars[2][0]) {
            if (chars[0][2] == 'X') {
                winnerSign = 'X';
            } else if (chars[0][2] == 'O') {
                winnerSign = 'O';
            }
        }

        if (winnerSign != 0) {
            System.out.println(winnerSign + " wins");
            hasWinner = true;
        }

        if (countTurns == 9) {
            System.out.println("Draw");
            hasWinner = true;
        }
    }
}
