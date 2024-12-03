package main.java.day02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day02Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day02data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        int safeReports = 0;

        while (scanner.hasNextLine()) {
            int[] report = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            boolean isReportSafe = true;

            if (report[0] > report[1]) {
                for (int i = 1; i < report.length; i++) {
                    int difference = report[i - 1] - report[i];
                    if (difference < 1 || difference > 3) {
                        isReportSafe = false;
                        break;
                    }
                }
            } else if (report[0] < report[1]) {
                for (int i = 1; i < report.length; i++) {
                    int difference = report[i] - report[i - 1];
                    if (difference < 1 || difference > 3) {
                        isReportSafe = false;
                        break;
                    }
                }
            } else {
                isReportSafe = false;
            }

            if (isReportSafe) {
                safeReports++;
            }
        }

        System.out.println("Number of safe reports: " + safeReports);
    }
}