package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int countOfSentences;
        int countOfWords = 0;

        String[] strArr = str.split("[.!?]");
        countOfSentences = strArr.length;
        for (String s : strArr) {
            countOfWords += s.split(" ").length;
        }
        System.out.println(countOfWords / countOfSentences > 10 ? "HARD" : "EASY");
    }
}