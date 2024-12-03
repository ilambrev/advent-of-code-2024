package main.java.day02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day02Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day02data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        int safeReports = 0;

        while (scanner.hasNextLine()) {
            int[] report = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (report[0] == report[1]) {
                int[] newReport = removeReportElement(report, 0);

                if (isReportSafe(newReport)) {
                    safeReports++;
                }
            } else if (isReportSafe(report)) {
                safeReports++;
            } else {
                for (int i = 0; i < report.length; i++) {
                    if (isReportSafe(removeReportElement(report, i))) {
                        safeReports++;
                        break;
                    }
                }
            }
        }

        System.out.println("Number of safe reports: " + safeReports);
    }

    public static boolean isReportSafe(int[] report) {
        if (report[0] > report[1]) {
            for (int i = 1; i < report.length; i++) {
                int difference = report[i - 1] - report[i];
                if (difference < 1 || difference > 3) {
                    return false;
                }
            }
        } else if (report[0] < report[1]) {
            for (int i = 1; i < report.length; i++) {
                int difference = report[i] - report[i - 1];
                if (difference < 1 || difference > 3) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static int[] removeReportElement(int[] report, int index) {
        int[] newReport = new int[report.length - 1];

        int newReportIndex = 0;

        for (int i = 0; i < report.length; i++) {
            if (i != index) {
                newReport[newReportIndex] = report[i];
                newReportIndex++;
            }
        }

        return newReport;
    }
}