import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static int levelOfDifficulty;
    public static Integer[] digitsArray;
    public static int turnNumber = 1;
    public static StringBuilder codeBuilder = new StringBuilder();
    public static String codeString;
    public static String inputString;
    public static boolean gameOn = true;

    public static void startGame() {

        levelOfDifficulty = 0;

        System.out.println("Please, enter the secret code's length:");

        try {
            levelOfDifficulty = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Exit");
        }

        if (levelOfDifficulty > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            System.exit(0);
        }

        digitsArray = new Integer[levelOfDifficulty];

        System.out.println("Okay, let's start a game!");

        digitsArray[0] = ThreadLocalRandom.current().nextInt(9);

        while (digitsArray[0] == 0) {
            digitsArray[0] = ThreadLocalRandom.current().nextInt(9);
        }

        for (int i = 1; i < digitsArray.length; i++) {
            digitsArray[i] = ThreadLocalRandom.current().nextInt(9);
            for (int j = 0; j < i; j++) {
                if ((digitsArray[j].equals(digitsArray[i]))) {
                    digitsArray[i] = ThreadLocalRandom.current().nextInt(9);
                    j = 0;
                } else if ((digitsArray[i].equals(digitsArray[0]))) {
                    digitsArray[i] = ThreadLocalRandom.current().nextInt(9);
                    j = 0;
                }
            }
        }
        for (int a : digitsArray) {
            codeBuilder.append(a);
        }
        codeString = codeBuilder.toString();
    }
    public static void nextTurn() {
        System.out.println("Turn " + turnNumber + ":");
        turnNumber++;
        inputString = scanner.nextLine();
    }

    public static void main(String[] args) {

        startGame();
        while (gameOn) {
//            System.out.println(codeString);
            nextTurn();
            guessing();
        }
        System.exit(0);
    }

    static void guessing() {

        int bull = 0;
        int cow = 0;

        StringBuilder domesticTry = new StringBuilder(codeString);

        for (int i = 0; i < domesticTry.length(); i++) {
            if (inputString.charAt(i) == domesticTry.charAt(i) && domesticTry.charAt(i) != 'X') {
                bull++;
                domesticTry.setCharAt(i, 'X');
            }
        }

        for (int i = 0; i < domesticTry.length(); i++) {
            if (domesticTry.charAt(i) != 'X') {
                for (int j = 0; j < domesticTry.length(); j++) {
                    if (domesticTry.charAt(i) == inputString.charAt(j)) {
                        cow++;
                        break;
                    }
                }
            }
        }

        if (domesticTry.length() == 1 && bull == 1) {
            System.out.println("Grade: 1 bull");
            System.out.println("Congratulations! You guessed the secret code.");
            gameOn = false;
        } else if (bull == 1 && codeString.length() == 1) {
            System.out.println("Grade: 1 bull");
            System.out.println("Congratulations! You guessed the secret code.");
            gameOn = false;
        } else if (bull == codeString.length() && codeString.length() > 1) {
            System.out.println("Grade: " + bull + " bulls");
            System.out.println("Congratulations! You guessed the secret code.");
            gameOn = false;
        } else if (bull == 1 && cow == 0) {
            System.out.println("Grade: 1 bull");
        } else if (bull == 0 && cow == 1) {
            System.out.println("Grade: 1 cow");
        } else if (bull > 0 && cow == 0) {
            System.out.println("Grade: " + bull + " bulls");
        } else if (bull == 0 && cow == 0) {
            System.out.println("Grade: None");
        } else if (bull == 1 && cow == 1) {
            System.out.println("Grade: 1 bull and 1 cow");
        } else if (bull > 1 && cow == 1) {
            System.out.println("Grade: " + bull + " bulls and 1 cow");
        } else if (bull == 1 && cow > 1) {
            System.out.println("Grade: bull and " + cow + " cows");
        } else {
            System.out.println("Grade: " + bull + " bulls and " + cow + " cow");
        }
    }
}
