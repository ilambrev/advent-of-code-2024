package main.java.day04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day04data.txt");
        Scanner scanner = new Scanner(fileInputStream);
        Pattern patternForStraightOrder = Pattern.compile("MAS");
        Pattern patternForReverseOrder = Pattern.compile("SAM");

        char[][] textArray = readFileInput(scanner);

        scanner.close();

        int rows = textArray.length;
        int cols = textArray[0].length;
        int xmasMatches = 0;

        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < cols - 2; j++) {
                String rightDiag = getRightDiag(textArray, i, j);
                String leftDiag = getLeftDiag(textArray, i, j);

                if (isMatchFound(rightDiag, patternForStraightOrder, patternForReverseOrder) &&
                        isMatchFound(leftDiag, patternForStraightOrder, patternForReverseOrder)) {
                    xmasMatches++;
                }
            }
        }

        System.out.println("Count of 'X-MAS' matches: " + xmasMatches);
    }

    public static boolean isMatchFound(String text, Pattern patternForStraightOrder, Pattern patternForReverseOrder) {
        Matcher matcherForStraightOrder = patternForStraightOrder.matcher(text);
        Matcher matcherForReverseOrder = patternForReverseOrder.matcher(text);

        return matcherForStraightOrder.find() || matcherForReverseOrder.find();
    }

    public static String getRightDiag(char[][] textArray, int row, int col) {
        StringBuilder diag = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            diag.append(textArray[row + i][col + i]);
        }
        return diag.toString();
    }

    public static String getLeftDiag(char[][] textArray, int row, int col) {
        StringBuilder diag = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            diag.append(textArray[row + i][col + 2 - i]);
        }
        return diag.toString();
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