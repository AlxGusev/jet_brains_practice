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

                String line = scanner.nextLine();

                characters += line.replaceAll(" ", "").length();
                words += line.split(" ").length;
                sentences += line.split("[.!?]").length;

            }

            double score = 4.71 * (characters / (double) words) + 0.5 * (words / (double) sentences) - 21.43;

            String[] age = {"5-6", "6-7", "7-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24+"};

            BigDecimal bd = BigDecimal.valueOf(score);
            bd = bd.setScale(2, RoundingMode.FLOOR);

            System.out.println("Words: " + words);
            System.out.println("Sentences: " + sentences);
            System.out.println("Characters: " + characters);
            System.out.println("The score is: " + bd);
            System.out.printf("This text should be understood by %s-year-olds.", age[(int)Math.ceil(score) - 1]);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
