import java.util.Scanner;

public class Battleship {

    static Scanner scanner = new Scanner(System.in);
    static char[][] board = new char[10][11];
    static char[][] tagOfWar = new char[10][11];
    static char[] shot;
    static int carrier = 0;
    static int battleship = 0;
    static int submarine = 0;
    static int cruiser = 0;
    static int destroyer = 0;
    static int y1 = 0;
    static int x1 = 0;
    static int y2 = 0;
    static int x2 = 0;

    public static void main(String[] args) {
        placeShips();
        System.out.println("The game starts!");
        showBoard(tagOfWar);
        System.out.println("Take a shot!");
        int turn = 0;
        while (turn == 0) {
            if (makeShot()) {
                turn = 1;
                checkTheShot();
            }
        }
    }

    static boolean makeShot() {
        shot = scanner.nextLine().toCharArray();
        if (!(shot[0] > '@' && shot[0] < 'K')) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        if (shot.length > 2) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        } else {
            return true;
        }
    }

    static void checkTheShot() {
        for (char[] chars : board) {
            if (chars[0] == shot[0]) {
                y1 = conversionCharToInt(shot[0]);
                break;
            }
        }
        x1 = shot.length <= 2 ? shot[1] - '0' : 10;

        if (board[y1][x1] == 'O') {
            board[y1][x1] = 'X';
            tafOfWarBoard();
            showBoard(tagOfWar);
            System.out.println("You hit a ship!");
            showBoard(board);
        } else {
            board[y1][x1] = 'M';
            tafOfWarBoard();
            showBoard(tagOfWar);
            System.out.println("You missed!");
            showBoard(board);
        }
    }

    static void placeShips() {
        createBoard(board);
        createBoard(tagOfWar);

        showBoard(board);

        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");

        while (carrier == 0) {
            recordCoordinates();
            if (createAirCraftCarrier()) {
                if (coordinates()) {
                    carrier = 1;
                }
            }
        }
        showBoard(board);

        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        while (battleship == 0) {
            recordCoordinates();
            if (createBattleship()) {
                if (coordinates()) {
                    battleship = 1;
                }
            }
        }
        showBoard(board);

        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        while (submarine == 0) {
            recordCoordinates();
            if (createSubmarine()) {
                if (coordinates()) {
                    submarine = 1;
                }
            }
        }
        showBoard(board);

        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        while (cruiser == 0) {
            recordCoordinates();
            if (createCruiser()) {
                if (coordinates()) {
                    cruiser = 1;
                }
            }
        }
        showBoard(board);

        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        while (destroyer == 0) {
            recordCoordinates();
            if (createDestroyer()) {
                if (coordinates()) {
                    destroyer = 1;
                }
            }
        }
        showBoard(board);
    }
    static void createBoard(char[][] array) {
        int k = 0;
        for (char j = 'A'; j < 'K'; j++) {
            array[k][0] = j;
            k++;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array[0].length; j++) {
                array[i][j] = '~';
            }
        }
    }
    static void tafOfWarBoard() {
        tagOfWar[y1][x1] = board[y1][x1];
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
    static void recordCoordinates() {
        String[] ship = scanner.nextLine().split(" ");

        for (char[] chars : board) {
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
    static boolean coordinates() {

        if (y1 - y2 == 0) {
            if (checkHorizontalCoordinates()) {
                for (int i = x1; i <= x2; i++) {
                    board[y1][i] = 'O';
                }
            } else {
                return false;
            }
            return true;
        } else if (x1 - x2 == 0) {
            if (checkVerticalCoordinates()) {
                for (int i = y1; i <= y2; i++) {
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
    static boolean checkHorizontalCoordinates() {

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
    static boolean checkVerticalCoordinates() {
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