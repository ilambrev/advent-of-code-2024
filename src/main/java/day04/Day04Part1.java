package main.java.day04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day04data.txt");
        Scanner scanner = new Scanner(fileInputStream);
        Pattern patternForStraightOrder = Pattern.compile("XMAS");
        Pattern patternForReverseOrder = Pattern.compile("SAMX");

        char[][] textArray = readFileInput(scanner);

        scanner.close();

        int rows = textArray.length;
        int cols = textArray[0].length;
        int diags = rows + cols - 1;
        long wordMatches = 0L;

        for (char[] chars : textArray) {
            wordMatches += countMatches(String.valueOf(chars), patternForStraightOrder, patternForReverseOrder);
        }

        for (int i = 0; i < cols; i++) {
            StringBuilder col = new StringBuilder();
            for (int j = 0; j < rows; j++) {
                col.append(textArray[j][i]);
            }
            wordMatches += countMatches(col.toString(), patternForStraightOrder, patternForReverseOrder);
        }

        for (int i = 0; i < diags; i++) {
            StringBuilder diagRight = new StringBuilder();
            StringBuilder diagLeft = new StringBuilder();
            if (i < rows) {
                for (int j = 0; j <= i; j++) {
                    diagRight.append(textArray[j][i - j]);
                    diagLeft.append(textArray[j][cols - 1 - (i - j)]);
                }
            } else {
                for (int j = rows - 1; j > i - rows; j--) {
                    diagRight.append(textArray[i - j][j]);
                    diagLeft.append(textArray[i - j][cols - 1 - j]);
                }
            }
            wordMatches += countMatches(diagRight.toString(), patternForStraightOrder, patternForReverseOrder);
            wordMatches += countMatches(diagLeft.toString(), patternForStraightOrder, patternForReverseOrder);
        }

        System.out.println("Count of word 'XMAS' appearances: " + wordMatches);
    }

    public static long countMatches(String text, Pattern patternForStraightOrder, Pattern patternForReverseOrder) {
        Matcher matcherForStraightOrder = patternForStraightOrder.matcher(text);
        Matcher matcherForReverseOrder = patternForReverseOrder.matcher(text);

        return matcherForStraightOrder.results().count() + matcherForReverseOrder.results().count();
    }

    public static char[][] readFileInput(Scanner scanner) {
        List<char[]> rows = new ArrayList<>();

        while (scanner.hasNextLine()) {
            char[] row = scanner.nextLine().toCharArray();
            rows.add(row);
        }

        char[][] textArray = new char[rows.size()][rows.get(0).length];

        for (int i = 0; i < rows.size(); i++) {
            textArray[i] = rows.get(i);
        }

        return textArray;
    }
}