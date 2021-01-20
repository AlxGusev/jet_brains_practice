import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static int levelOfDifficulty;
    public static char[] charArray;
    public static int turnNumber = 1;
    public static String codeString;
    public static String inputString;
    public static boolean gameOn = true;
    public static int secretCodeLength;




    public static void startGame() {

        levelOfDifficulty = 0;

        System.out.println("Input the length of the secret code:");
        secretCodeLength = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the number of possible symbols in the code:");
        levelOfDifficulty = Integer.parseInt(scanner.nextLine());

        if (levelOfDifficulty > 36) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            System.exit(0);
        }

        char[] alphabet = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

        StringBuilder currentLevelOfDifficulty = new StringBuilder();
        currentLevelOfDifficulty.append(alphabet, 0, levelOfDifficulty);

        charArray = new char[secretCodeLength];

        for (int i = 0; i < charArray.length; i++) {
            int randomPickFromAlphabet = ThreadLocalRandom.current().nextInt(levelOfDifficulty);
            charArray[i] = currentLevelOfDifficulty.charAt(randomPickFromAlphabet);
            for (int j = 0; j < i; j++) {
                if (charArray[j] == charArray[i]) {
                    randomPickFromAlphabet = ThreadLocalRandom.current().nextInt(levelOfDifficulty);
                    charArray[i] = currentLevelOfDifficulty.charAt(randomPickFromAlphabet);
                    j = 0;
                }
            }
        }

        if (levelOfDifficulty <= 10) {
            System.out.println("The secret is prepared: " + "*".repeat(secretCodeLength) + " " +
                    "(" + currentLevelOfDifficulty.charAt(0) + "-" + currentLevelOfDifficulty.charAt(levelOfDifficulty - 1) + ").");
        } else {
            System.out.println("The secret is prepared: " + "*".repeat(secretCodeLength) + " " +
                    "(0-9, " + currentLevelOfDifficulty.charAt(10) + "-" + currentLevelOfDifficulty.charAt(levelOfDifficulty - 1) + ").");
        }

        System.out.println("Okay, let's start a game!");

    }
    public static void nextTurn() {
        System.out.println("Turn " + turnNumber + ":");
        turnNumber++;
        inputString = scanner.nextLine();
    }

    public static void main(String[] args) {

        startGame();
        while (gameOn) {
            nextTurn();
            guessing();
        }
        System.exit(0);
    }

    static void guessing() {

        int bull = 0;
        int cow = 0;

        StringBuilder domesticTry = new StringBuilder();
        domesticTry.append(charArray, 0, secretCodeLength);


        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == domesticTry.charAt(i) && domesticTry.charAt(i) != 'X') {
                bull++;
                domesticTry.setCharAt(i, 'X');
            }
        }

        for (int i = 0; i < inputString.length(); i++) {
            if (domesticTry.charAt(i) != 'X') {
                for (int j = 0; j < inputString.length(); j++) {
                    if (domesticTry.charAt(i) == inputString.charAt(j)) {
                        cow++;
                        break;
                    }
                }
            }
        }

        if (charArray.length == 1 && bull == 1) {
            System.out.println("Grade: 1 bull");
            System.out.println("Congratulations! You guessed the secret code.");
            gameOn = false;
        } else if (bull == charArray.length && charArray.length > 1) {
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
