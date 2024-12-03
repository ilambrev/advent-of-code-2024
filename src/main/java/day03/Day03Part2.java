package main.java.day03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day03data.txt");
        Scanner scanner = new Scanner(fileInputStream);
        StringBuilder memoryContent = new StringBuilder();

        while (scanner.hasNext()) {
            memoryContent.append(scanner.next());
        }

        scanner.close();

        Pattern pattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)|don't\\(\\)|do\\(\\)");
        Matcher matcher = pattern.matcher(memoryContent);

        int sum = 0;
        boolean isMulEnabled = true;

        while (matcher.find()) {
            String match = matcher.group();

            if (match.contains("mul") && isMulEnabled) {
                match = match.replace("mul(", "");
                match = match.replace(")", "");

                int[] nums = Arrays.stream(match.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                sum += nums[0] * nums[1];
            }

            if (match.equals("don't()")) {
                isMulEnabled = false;
            }

            if (match.equals("do()")) {
                isMulEnabled = true;
            }
        }

        System.out.println("Result: " + sum);
    }
}