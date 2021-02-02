import java.io.File;
import java.util.Scanner;

public class ReadAbility {

    static Scanner scanner = new Scanner(System.in);

    static int characters = 0;
    static int words = 0;
    static int sentencesCount = 0;
    static int syllables = 0;
    static int polysyllables = 0;
    static double S = 0;
    static double L = 0;
    static String[] age = {"6", "7", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "24", "24"};
    static double score = 0;

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(new File(args[0]))) {

            System.out.println("The The text is:");


            String line = scanner.nextLine();
            System.out.println(line);

            characters += line.replaceAll(" ", "").length();
            words += line.split(" ").length;
            sentencesCount += line.split("[.!?]").length;

            line = line.toLowerCase().replaceAll("e\\b", "")
                                    .replaceAll("[aoeiuy]{1,2}", "o")
                                    .replaceAll("[.,!?:;]", "")
                                    .replaceAll("\\d+", "");
            String[] arr = line.split(" ");
            for (String str : arr) {
                int count = 0;
                if (str.length() <= 3) {
                    syllables++;
                } else {
                    for (int i = 0; i < str.length(); i++) {
                        if(str.charAt(i) == 'o') {
                            count++;
                        }
                    }
                    if (count >= 3) {
                        polysyllables++;
                    }
                    syllables += count;
                }
            }

            System.out.println("\nWords: " + words);
            System.out.println("Sentences: " + sentencesCount);
            System.out.println("Characters: " + characters);
            System.out.println("Syllables: " + syllables);
            System.out.println("Polysyllables: " + polysyllables);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        readabilityMethodMenu();
    }

    static void readabilityMethodMenu() {

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        String pick = scanner.nextLine();

        switch (pick) {
            case "ARI":
                score = ari();
                break;
            case "FK":
                score = fk();
                break;
            case "SMOG":
                score = smog();
                break;
            case "CL":
                score = cl();
                break;
            case "all":
                score = allMethods();
                break;
        }

        System.out.printf("This text should be understood by %s-year-olds.", score);

    }

    static double ari() {
        return 4.71 * (characters / (double) words) + 0.5 * (words / (double) sentencesCount) - 21.43;
    }


    static double fk() {
        return 0.39 * (words / (double) sentencesCount) + 11.8 * (syllables / (double) words) - 15.59;
    }

    static double smog() {
        return 1.043 * Math.sqrt(polysyllables * (30 / (double) sentencesCount)) + 3.1291;
    }

    static double cl() {
        L = (characters / (double) words) * 100;
        S = (sentencesCount / (double) words) * 100;
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    static double allMethods() {
        double ari = ari();
        double fk = fk();
        double smog = smog();
        double cl = cl();

        int scoreAri = Integer.parseInt(age[(int)Math.round(ari) - 1]);
        int scoreFk = Integer.parseInt(age[(int)Math.round(fk) - 1]);
        int scoreSmog = Integer.parseInt(age[(int)Math.round(smog) - 1]);
        int scoreCl = Integer.parseInt(age[(int)Math.round(cl) - 1]);

        System.out.printf("\nAutomated Readability Index: %.2f (about %d-year-olds)\n", ari, scoreAri);
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds)\n", fk, scoreFk);
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds)\n", smog, scoreSmog);
        System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds)\n\n", cl, scoreCl);
        return (scoreAri + scoreFk + scoreSmog + scoreCl) / (double) 4;
    }
}
