package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int symb = str.length();

        System.out.println(symb > 100 ? "HARD" : "EASY");
    }
}
