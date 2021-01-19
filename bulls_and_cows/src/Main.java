import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int turnNumber = 1;
    public static StringBuilder codeString = new StringBuilder();
    public static StringBuilder guessString = new StringBuilder();
    public static int bull = 0;
    public static int cow = 0;
    public static boolean gameOn = true;


    public static Scanner scanner = new Scanner(System.in);

    public static void startGame() {

        int number = 0;

        System.out.println("Please, enter the secret code's length:");

        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Exit");
        }

        if (number > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            System.exit(0);
        }

        System.out.println("Okay, let's start a game!");

        Integer[] digitsArray = new Integer[number];

        digitsArray[0] = ThreadLocalRandom.current().nextInt(9);

        while (digitsArray[0] == 0) {
            digitsArray[0] = ThreadLocalRandom.current().nextInt(9);
        }

        codeString.append(digitsArray[0]);

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
            codeString.append(digitsArray[i]);
        }
    }

    public static void main(String[] args) {

        startGame();
        while (bull != 4) {
            nextTurn();
            guessing(codeString);
        }


    }

    public static void nextTurn() {
        System.out.println("Turn " + turnNumber + ":");
        turnNumber++;
    }

    static void guessing(StringBuilder code) {

        String input = scanner.nextLine();

        guessString = new StringBuilder(input);

        if (code.charAt(0) == guessString.charAt(0) &&
                code.charAt(1) == guessString.charAt(1) &&
                code.charAt(2) == guessString.charAt(2) &&
                code.charAt(3) == guessString.charAt(3)) {

            System.out.println("Grade: " + 4 + " bull(s). The secret code is " + code.charAt(0) + code.charAt(1) + code.charAt(2) + code.charAt(3) + ".");
            System.exit(0);
        }

        if (guessString.charAt(0) == code.charAt(0)) {
            bull++;
        } else {
            for (int i = 1; i < code.length(); i++) {
                if (code.charAt(i) == guessString.charAt(0)) {
                    cow++;
                }
            }
        }

        if (guessString.charAt(1) == code.charAt(1)) {
            bull++;
        } else {
            for (int i = 1; i < code.length(); i++) {
                if (code.charAt(i) == guessString.charAt(1)) {
                    cow++;
                }
            }
        }

        if (guessString.charAt(2) == code.charAt(2)) {
            bull++;
        } else {
            for (int i = 1; i < code.length(); i++) {
                if (code.charAt(i) == guessString.charAt(2)) {
                    cow++;
                }
            }
        }

        if (guessString.charAt(3) == code.charAt(3)) {
            bull++;
        } else {
            for (int i = 1; i < code.length(); i++) {
                if (code.charAt(i) == guessString.charAt(3)) {
                    cow++;
                }
            }
        }

//        if (bull == 0 && cow == 0) {
//            System.out.println("Grade: None. The secret code is " + code.charAt(0) + code.charAt(1) + code.charAt(2) + code.charAt(3) + ".");
//        } else if (bull == 0) {
//            System.out.println("Grade: " + cow + " cow(s). The secret code is " + code.charAt(0) + code.charAt(1) + code.charAt(2) + code.charAt(3) + ".");
//        } else {
//            System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s). The secret code is " + code.charAt(0) + code.charAt(1) + code.charAt(2) + code.charAt(3) + ".");
//        }
        if (bull == 0 && cow == 0) {
            System.out.println("Grade: None.");
        } else if (bull == 0 && cow == 1) {
            System.out.println("Grade: " + cow + " cow");
        } else {
            System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s). The secret code is .");
        }



    }

//    static void guess(StringBuilder code) {
//
//        String guess = scanner.nextLine();
//
//        StringBuilder sb = new StringBuilder(guess);
//
//
//
//        int bull = 0;
//        int cow = 0;
//
//        int firstDigit = Integer.parseInt(guess[0]);
//        int secondDigit = Integer.parseInt(guess[1]);
//        int thirdDigit = Integer.parseInt(guess[2]);
//        int fourthDigit = Integer.parseInt(guess[3]);
//
//        if (firstDigit == code[0] &&
//                secondDigit == code[1] &&
//                thirdDigit == code[2] &&
//                fourthDigit == code[3]) {
//            System.out.println("Grade: " + 4 + " bull(s). The secret code is " + code[0] + code[1] + code[2] + code[3] + ".");
//            System.exit(0);
//        }
//
//        if (firstDigit == code[0]) {
//            bull++;
//        } else {
//            for (int i = 1; i < code.length; i++) {
//                if (code[i] == firstDigit) {
//                    cow++;
//                }
//            }
//        }
//
//        if (secondDigit == code[1]) {
//            bull++;
//        } else {
//            for (Integer integer : code) {
//                if (integer == secondDigit) {
//                    cow++;
//                }
//            }
//        }
//
//        if (thirdDigit == code[2]) {
//            bull++;
//        } else {
//            for (Integer integer : code) {
//                if (integer == thirdDigit) {
//                    cow++;
//                }
//            }
//        }
//
//        if (fourthDigit == code[3]) {
//            bull++;
//        } else {
//            for (Integer integer : code) {
//                if (integer == fourthDigit) {
//                    cow++;
//                }
//            }
//        }
//        if (bull == 0 && cow == 0) {
//            System.out.println("Grade: None. The secret code is " + code[0] + code[1] + code[2] + code[3] + ".");
//        } else if (bull == 0) {
//            System.out.println("Grade: " + cow + " cow(s). The secret code is " + code[0] + code[1] + code[2] + code[3] + ".");
//        } else {
//            System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s). The secret code is " + code[0] + code[1] + code[2] + code[3] + ".");
//        }
//    }
}
