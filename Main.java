package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;;

public class Main {
    public static void main(String[] args) {
        StringBuilder strB = new StringBuilder();
        String str = "";

        // get Text
        if (args.length != 0) {
            File file = new File(args[0]);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    strB.append(scanner.nextLine());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // get Text's Info
            str = strB.toString();

            // [characters, syllables, polysyllables, wordsCount, sentencesCount]
            int[] strInfo = getStrInfo(str);

            int characters = strInfo[0];
            int syllables = strInfo[1];
            int polysyllables = strInfo[2];
            int words = strInfo[3];
            int sentences = strInfo[4];
            double avgCharsPer100words = (double) characters / words * 100d;
            double avgSentencesPer100Words = (double) sentences / words * 100d;


            // Output Info
            System.out.printf("""
                    The text is:
                    %s

                    Words: %d
                    Sentences: %d
                    Characters: %d
                    Syllables: %d
                    Polysyllables: %d
                    Enter the score you want to calculate (ARI, FK, SMOG, CL, all):\s""",
                    str, words, sentences, characters, syllables, polysyllables
            );
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println();

            switch (input) {

                case "ARI":
                    double scoreAri = ari(words, characters, sentences);
                    int yearAri = (int)(Math.ceil(scoreAri) + 5);
                    System.out.printf("Automated Readability Index: %3.2f (about %d-year-olds).",
                            scoreAri, yearAri);
                    break;

                case "FK":
                    double scoreFk = fk(words, syllables, sentences);
                    int yearFk = (int)(Math.ceil(scoreFk) + 5);
                    System.out.printf("Flesch–Kincaid readability tests: %3.2f (about %d-year-olds).",
                            scoreFk, yearFk);
                    break;

                case "SMOG":
                    double scoreSmog = smog(polysyllables, sentences);
                    int yearSmog = (int)(Math.ceil(scoreSmog) + 5);
                    System.out.printf("Simple Measure of Gobbledygook: %3.2f (about %d-year-olds).",
                            scoreSmog, yearSmog);
                    break;

                case "CL":
                    double scoreCl = cl(avgCharsPer100words, avgSentencesPer100Words);
                    int yearCl = (int)(Math.ceil(scoreCl) + 5);
                    System.out.printf("Coleman–Liau index: %3.2f (about %d-year-olds).",
                            scoreCl, yearCl);
                    break;

                case "all":
                    double aScoreAri = ari(words, characters, sentences);
                    int aYearAri = (int)(Math.ceil(aScoreAri) + 5);
                    System.out.printf("Automated Readability Index: %3.2f (about %d-year-olds).\n",
                            aScoreAri, aYearAri);

                    double aScoreFk = fk(words, syllables, sentences);
                    int aYearFk = (int)(Math.ceil(aScoreFk) + 5);
                    System.out.printf("Flesch–Kincaid readability tests: %3.2f (about %d-year-olds).\n",
                            aScoreFk, aYearFk);

                    double aScoreSmog = smog(polysyllables, sentences);
                    int aYearSmog = (int)(Math.ceil(aScoreSmog) + 5);
                    System.out.printf("Simple Measure of Gobbledygook: %3.2f (about %d-year-olds).\n",
                            aScoreSmog, aYearSmog);

                    double aScoreCl = cl(avgCharsPer100words, avgSentencesPer100Words);
                    int aYearCl = (int)(Math.ceil(aScoreCl) + 5);
                    System.out.printf("Coleman–Liau index: %3.2f (about %d-year-olds).\n",
                            aScoreCl, aYearCl);
                    System.out.println();

                    double avgAge = (aYearAri + aYearFk + aYearSmog + aYearCl) / 4d;
                    System.out.printf("This text should be understood in average by %3.2f-year-olds.", avgAge);

                    break;

                default:
                    System.out.println("error");
                    break;
            }



        }
    }

    public static int[] getStrInfo(String str) {

        // Count of Syllables.
        char[] allVowels = {'A', 'E', 'I', 'O', 'U', 'Y', 'a', 'e', 'i', 'o', 'u', 'y'};
        String nStr = new String(str).replace(",", "");
        String[] words = nStr.split("\\W+");
        int characters = new String(str).replace(" ", "").length();
        int syllables = 0;
        int polysyllables = 0;
        int wordsCount = words.length;
        int sentencesCount = str.split("[.!?]").length;

        for (String word : words) {
            int vowels = 0;
            for (int i = 0; i < word.length(); i++) {
                for (char ch : allVowels) {
                    if (word.charAt(i) == ch) {
                        if ((i != 0) && String.valueOf(word.charAt(i - 1)).matches("[AEIOUYaeiouy]")) {
                            break;
                        }
                        if (i == word.length() - 1 && word.charAt(i) == 'e') {
                            break;
                        }
                        vowels++;
                        break;
                    }
                }
            }
            syllables += (vowels == 0) ? 1 : vowels;
            polysyllables += (vowels > 2) ? 1 : 0;
        }
        int[] strInfo = {characters, syllables, polysyllables, wordsCount, sentencesCount};
        return strInfo;
    }


    public static double ari(int words, int characters, int sentences) {
        double score = 4.71 * characters / words + 0.5 * words / sentences - 21.43;
        return score;
    }

    public static double fk(int words, int syllables, int sentences) {
        double score = 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
        return score;
    }

    public static double smog(int polysyllables, int sentences) {
        double score = 1.043 * Math.sqrt((double) polysyllables * 30 / sentences) + 3.1291;
        return score;
    }

    public static double cl(double avgCharsPer100words, double avgSentencesPer100Words) {
        double score = 0.0588 * avgCharsPer100words - 0.296 * avgSentencesPer100Words - 15.8;
        return score;
    }
}