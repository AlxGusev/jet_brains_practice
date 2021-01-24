import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battleship {

    static Scanner scanner = new Scanner(System.in);
    static char[][] boardP1 = new char[10][11];
    static char[][] boardP2 = new char[10][11];
    static char[][] tagOfWar1 = new char[10][11];
    static char[][] tagOfWar2 = new char[10][11];
    static char[] shot;
    static List<String> fiveDeckCarrierP1 = new ArrayList<>();
    static List<String> fourDeckBattleshipP1 = new ArrayList<>();
    static List<String> threeDeckSubmarineP1 = new ArrayList<>();
    static List<String> threeDeckCruiserP1 = new ArrayList<>();
    static List<String> twoDeckDestroyerP1 = new ArrayList<>();
    static List<String> fiveDeckCarrierP2 = new ArrayList<>();
    static List<String> fourDeckBattleshipP2 = new ArrayList<>();
    static List<String> threeDeckSubmarineP2 = new ArrayList<>();
    static List<String> threeDeckCruiserP2 = new ArrayList<>();
    static List<String> twoDeckDestroyerP2 = new ArrayList<>();
    static List<List<String>> fleet1 = new ArrayList<>();
    static List<List<String>> fleet2 = new ArrayList<>();
    static List<String> madeShots1 = new ArrayList<>();
    static List<String> madeShots2 = new ArrayList<>();
    static int shipsLeftP1 = 5;
    static int shipsLeftP2 = 5;
    static int carrier = 0;
    static int battleship = 0;
    static int submarine = 0;
    static int cruiser = 0;
    static int destroyer = 0;
    static int y1 = 0;
    static int x1 = 0;
    static int y2 = 0;
    static int x2 = 0;
    static int turn = 1;
    static boolean game = false;

    public static void main(String[] args) {

        System.out.println("Player 1, place your ships on the game field");
        placeShips(fiveDeckCarrierP1, fourDeckBattleshipP1, threeDeckSubmarineP1, threeDeckCruiserP1, twoDeckDestroyerP1, boardP1, tagOfWar1);
        turn = 2;
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        System.out.println("Player 2, place your ships on the game field");
        placeShips(fiveDeckCarrierP2, fourDeckBattleshipP2, threeDeckSubmarineP2, threeDeckCruiserP2, twoDeckDestroyerP2, boardP2, tagOfWar2);
        turn = 1;

        while(!game) {

            if (turn == 1) {
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                showBoard(tagOfWar2);
                System.out.println("---------------------");
                showBoard(boardP1);
                System.out.println("Player 1, it's your turn:");
                boolean shorIsCorrect = false;
                while (!shorIsCorrect) {
                    shorIsCorrect = makeShot();
                }
                checkTheShot(boardP2, tagOfWar2, fleet2, shipsLeftP2, madeShots1);
                turn = 2;
            } else {
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                showBoard(tagOfWar1);
                System.out.println("---------------------");
                showBoard(boardP2);
                System.out.println("Player 2, it's your turn:");
                boolean shorIsCorrect = false;
                while (!shorIsCorrect) {
                    shorIsCorrect = makeShot();
                }
                checkTheShot(boardP1, tagOfWar1, fleet1, shipsLeftP1, madeShots2);
                turn = 1;
            }

        }
        if (turn == 1) {
            System.out.println("Player 1 is winner");
        } else {
            System.out.println("Player 2 is winner");
        }
        System.exit(0);

    }


    static void checkTheShot(char[][] board, char[][] tagOfWar, List<List<String>> fleet, int shipsLeft, List<String> madeShots) {
        for (char[] chars : board) {
            if (chars[0] == shot[0]) {
                y1 = conversionCharToInt(shot[0]);
                break;
            }
        }
        x1 = shot.length <= 2 ? shot[1] - '0' : 10;

        String hitOrMiss = "";
        if (!checkDoubleHit(hitOrMiss, board, madeShots)) {
            if (board[y1][x1] == 'O') {
                board[y1][x1] = 'X';
            } else {
                board[y1][x1] = 'M';
            }
            tagOfWar[y1][x1] = board[y1][x1];
            hitOrMiss = y1 + "" + x1;
            checkHit(hitOrMiss, fleet, shipsLeft);
        } else {
            System.out.println("You hit a ship!");
        }
    }
    static boolean checkDoubleHit(String hitOrMiss, char[][] board, List<String> madeShots) {

        if (madeShots.contains(hitOrMiss) && board[y1][x1] == 'X') {
            return true;
        } else {
            madeShots.add(hitOrMiss);
            return false;
        }
    }
    static void checkHit(String hitOrMiss, List<List<String>> fleet, int shipsLeft) {
        int misses = 0;
        for (List<String> ship : fleet) {
            if (ship.contains(hitOrMiss)) {
                if (shipsLeft == 1 && ship.size() == 1) {
                    ship.remove(hitOrMiss);
                    if (turn == 1) {
                        shipsLeftP2--;
                    } else {
                        shipsLeftP1--;
                    }
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    game = true;
                    break;
                } else if (ship.size() == 1) {
                    ship.remove(hitOrMiss);
                    if (turn == 1) {
                        shipsLeftP2--;
                    } else {
                        shipsLeftP1--;
                    }
                    System.out.println("You sank a ship!");
                    break;
                } else {
                    ship.remove(hitOrMiss);
                    System.out.println("You hit a ship!");
                    break;
                }
            } else {
                misses++;
            }
        }
        if (misses == 5) {
            System.out.println("You missed!");
        }
    }
    static boolean makeShot() {
        shot = scanner.nextLine().toCharArray();
        if (!(shot[0] > '@' && shot[0] < 'K')) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        if (shot.length > 2) {
            if (shot[1] == '1' && shot[2] == '0') {
                return true;
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }
    static boolean coordinates(List<String> ship, char[][] board) {

        if (y1 - y2 == 0) {
            if (checkHorizontalCoordinates(board)) {
                for (int i = x1; i <= x2; i++) {
                    ship.add(y1 + "" + i);
                    board[y1][i] = 'O';
                }
            } else {
                return false;
            }
            return true;
        } else if (x1 - x2 == 0) {
            if (checkVerticalCoordinates(board)) {
                for (int i = y1; i <= y2; i++) {
                    ship.add(i + "" + x1);
                    board[i][x1] = 'O';
                }
            } else {
                return false;
            }
            return true;
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
    }
    static void placeShips(List<String> ship1, List<String> ship2, List<String> ship3, List<String> ship4, List<String> ship5, char[][] board, char[][] tagOfWar) {

        createBoard(board);
        createBoard(tagOfWar);

        showBoard(board);

        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        while (carrier == 0) {
            recordCoordinates(board);
            if (createAirCraftCarrier()) {
                if (coordinates(ship1, board)) {
                    carrier = 1;
                }
            }
        }
        carrier = 0;
        showBoard(board);

        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        while (battleship == 0) {
            recordCoordinates(board);
            if (createBattleship()) {
                if (coordinates(ship2, board)) {
                    battleship = 1;
                }
            }
        }
        battleship = 0;
        showBoard(board);

        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        while (submarine == 0) {
            recordCoordinates(board);
            if (createSubmarine()) {
                if (coordinates(ship3, board)) {
                    submarine = 1;
                }
            }
        }
        submarine = 0;
        showBoard(board);

        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        while (cruiser == 0) {
            recordCoordinates(board);
            if (createCruiser()) {
                if (coordinates(ship4, board)) {
                    cruiser = 1;
                }
            }
        }
        cruiser = 0;
        showBoard(board);

        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        while (destroyer == 0) {
            recordCoordinates(board);
            if (createDestroyer()) {
                if (coordinates(ship5, board)) {
                    destroyer = 1;
                }
            }
        }
        destroyer = 0;

        if (turn == 1) {
            fleet1.add(ship1);
            fleet1.add(ship2);
            fleet1.add(ship3);
            fleet1.add(ship4);
            fleet1.add(ship5);
        } else {
            fleet2.add(ship1);
            fleet2.add(ship2);
            fleet2.add(ship3);
            fleet2.add(ship4);
            fleet2.add(ship5);
        }

        showBoard(board);

    }
    static void recordCoordinates(char[][] arr) {
        String[] ship = scanner.nextLine().split(" ");

        for (char[] chars : arr) {
            if (chars[0] == ship[0].charAt(0)) {
                y1 = conversionCharToInt(ship[0].charAt(0));
            }
            if (chars[0] == ship[1].charAt(0)) {
                y2 = conversionCharToInt(ship[1].charAt(0));
            }
        }

        x1 = ship[0].length() <= 2 ? ship[0].charAt(1) - '0' : 10;
        x2 = ship[1].length() <= 2 ? ship[1].charAt(1) - '0' : 10;

        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
    }
    static void createBoard(char[][] array) {
        int k = 0;
        for (char j = 'A'; j <= 'J'; j++) {
            array[k][0] = j;
            k++;

        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array[0].length; j++) {
                array[i][j] = '~';
            }
        }
    }
    static void showBoard(char[][] array) {

        int[] rows = new int[11];
        System.out.print("  ");
        for (int i = 1; i < rows.length; i++) {
            System.out.print(i + " ");
        }
        for (char[] strings : array) {
            System.out.println();
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(strings[j] + " ");
            }
        }
        System.out.println();
    }
    static boolean createAirCraftCarrier() {
        if (Math.abs(y1 - y2) == 4 || Math.abs(x1 - x2) == 4) {
            return true;
        }
        System.out.println("Error! Wrong length of the Aircraft Carrier! Try again:");
        return false;
    }
    static boolean createBattleship() {
        if (Math.abs(x1 - x2) == 3 || Math.abs(y1 - y2) == 3) {
            return true;
        }
        System.out.println("Error! Wrong length of the Submarine! Try again:");
        return false;
    }
    static boolean createSubmarine() {
        if (Math.abs(x1 - x2) == 2 || Math.abs(y1 - y2) == 2) {
            return true;
        }
        System.out.println("Error! Wrong length of the Submarine! Try again:");
        return false;
    }
    static boolean createCruiser() {
        if (Math.abs(x1 - x2) == 2 || Math.abs(y1 - y2) == 2) {
            return true;
        }
        System.out.println("Error! Wrong length of the Cruiser! Try again:");
        return false;
    }
    static boolean createDestroyer() {
        if (Math.abs(x1 - x2) == 1 || Math.abs(y1 - y2) == 1) {
            return true;
        }
        System.out.println("Error! Wrong length of the Destroyer! Try again:");
        return false;
    }
    static boolean checkHorizontalCoordinates(char[][] board) {

        if (y1 == 0) {
            if (x1 == 1) {
                for (int i = x1; i <= x2 + 1; i++) {
                    if (board[y1 + 1][i] != '~' || board[y1][x2 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (x2 != 10) {
                for (int i = x1 - 1; i <= x2 + 1; i++) {
                    if (board[y1 + 1][i] != '~' || board[y1][x1 - 1] != '~' || board[y1][x2 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = x1 - 1; i <= x2; i++) {
                    if (board[y1 + 1][i] != '~' || board[y1][x1 - 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        } else if (y1 != 9) {
            if (x1 == 1) {
                for (int i = x1; i <= x2 + 1; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1 + 1][i] != '~' || board[y1][x2 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (x2 != 10) {
                for (int i = x1 - 1; i <= x2 + 1; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1 + 1][i] != '~' || board[y1][x1 - 1] != '~'|| board[y1][x1 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = x1 - 1; i <= x2; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1 + 1][i] != '~' || board[y1][x1 - 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        } else {
            if (x1 == 1) {
                for (int i = x1; i <= x2 + 1; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1][x2 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (x2 != 10) {
                for (int i = x1 - 1; i <= x2 + 1; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1][x1 - 1] != '~' || board[y1][x2 + 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = x1 - 1; i <= x2; i++) {
                    if (board[y1 - 1][i] != '~' || board[y1][x1 - 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    static boolean checkVerticalCoordinates(char[][] board) {
        if (x1 == 1) {
            if (y1 == 0) {
                for (int i = y1; i <= y2 + 1; i++) {
                    if (board[i][x1 + 1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (y2 != 9) {
                for (int i = y1 - 1; i <= y2 + 1; i++) {
                    if (board[i][x1 + 1] != '~' || board[y1 - 1][x1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = y1 - 1; i <= y2; i++) {
                    if (board[i][x1 + 1] != '~' || board[y1 - 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        } else if (x1 != 10) {
            if (y1 == 0) {
                for (int i = y1; i <= y2 + 1; i++) {
                    if (board[i][x1 - 1] != '~' || board[i][x1 + 1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (y2 != 9) {
                for (int i = y1 - 1; i <= y2 + 1; i++) {
                    if (board[i][x1 - 1] != '~' || board[i][x1 + 1] != '~' || board[y1 - 1][x1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = y1 - 1; i <= y2; i++) {
                    if (board[i][x1 - 1] != '~' || board[i][x1 + 1] != '~' || board[y1 - 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        } else {
            if (y1 == 0) {
                for (int i = y1; i <= y2 + 1; i++) {
                    if (board[i][x1 - 1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else if (y2 != 9) {
                for (int i = y1 - 1; i <= y2 + 1; i++) {
                    if (board[i][x1 - 1] != '~' || board[y1 - 1][x1] != '~' || board[y2 + 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            } else {
                for (int i = y1 - 1; i <= y2; i++) {
                    if (board[i][x1 - 1] != '~' || board[y1 - 1][x1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    static int conversionCharToInt(char x) {
        switch (x) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            default:
                throw new IllegalStateException("Unexpected value: " + x);
        }
    }
}