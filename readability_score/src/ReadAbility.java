import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class ReadAbility {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(new File(args[0]))) {

            int characters = 0;
            int words = 0;
            int sentences = 0;

            while (scanner.hasNext()) {

                String[] sentence = scanner.nextLine().replaceAll("\\s+", " ").split("[!.?] ");
                sentences += sentence.length;
                characters += sentences - 1;

                for (String a : sentence) {
                    String[] b = a.split(" ");
                    words += b.length;

                    for (String c : b) {
                        characters += c.length();
                    }
                }
            }

            double score = 4.71 * (characters / (double) words) + 0.5 * (words / (double) sentences) - 21.43;

            BigDecimal bd = BigDecimal.valueOf(score);
            bd = bd.setScale(2, RoundingMode.FLOOR);

            System.out.printf("Words: %s\n", words);
            System.out.printf("Sentences: %s\n", sentences);
            System.out.printf("Characters: %s\n", characters);
            System.out.printf("The score is: %.2f\n", bd);
            System.out.printf("This text should be understood by %s-year-olds.", getGradeLevel((int)Math.ceil(score)));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static String getGradeLevel(int score) {
        switch (score) {
            case 1:
                return "5-6";
            case 2:
                return "6-7";
            case 3:
                return "7-9";
            case 4:
                return "9-10";
            case 5:
                return "10-11";
            case 6:
                return "11-12";
            case 7:
                return "12-13";
            case 8:
                return "13-14";
            case 9:
                return "14-15";
            case 10:
                return "15-16";
            case 11:
                return "16-17";
            case 12:
                return "17-18";
            case 13:
                return "18-24";
            case 14:
                return "24+";
            default:
                return "";
        }
    }
}
