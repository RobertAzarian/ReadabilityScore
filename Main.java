package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;;

public class Main {
    public static void main(String[] args) {

        // Ввод имени файла для индексации через args.
        StringBuilder strB = new StringBuilder();

        if (args.length != 0) {
            File file = new File(args[0]);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    strB.append(scanner.nextLine());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String str = strB.toString();
            int charsCount = new String(str).replace(" ", "").length();
            int wordsCount = str.split(" ").length;
            int sentencesCount = str.split("[.!?]").length;
            double score = 4.71 * charsCount / wordsCount + 0.5 * wordsCount / sentencesCount - 21.43;
            int age = (int)(Math.ceil(score) + 4);

            System.out.printf("""
                    The text is:
                    %s
                    
                    Words: %d
                    Sentences: %d
                    Characters: %d
                    The score is: %3.2f
                    This text should be understood by %d-%d years-old.
                    """,
                    str, wordsCount, sentencesCount, charsCount, score, age, age + 1
                    );
        }
    }
}