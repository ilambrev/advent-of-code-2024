package main.java.day05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day05Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day05data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        List<int[]> rules = new ArrayList<>();
        List<int[]> updates = new ArrayList<>();
        int sum = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("|")) {
                int[] rule = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
                rules.add(rule);
            }

            if (line.contains(",")) {
                int[] update = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                updates.add(update);
            }

        }

        for (int[] update : updates) {
            boolean isUpdateFollowsRules = true;
            for (int i = 0; i < update.length - 1; i++) {
                if (!isTheRuleFollowed(update[i], update[i + 1], rules)) {
                    isUpdateFollowsRules = false;
                    break;
                }
            }
            if (!isUpdateFollowsRules) {
                int[] reorderedUpdate = reorderUpdate(update, rules);
                sum += update[reorderedUpdate.length / 2];
            }
        }

        System.out.println("Sum of middle pages of reordered updates: " + sum);
    }

    public static boolean isTheRuleFollowed(int page1, int page2, List<int[]> rules) {
        for (int[] rule : rules) {
            if (page1 == rule[0] && page2 == rule[1]) {
                return true;
            }
        }
        return false;
    }

    public static int[] reorderUpdate(int[] update, List<int[]> rules) {
        for (int i = 0; i < update.length - 1; i++) {
            if (!isTheRuleFollowed(update[i], update[i + 1], rules)) {
                int page1 = update[i];
                update[i] = update[i + 1];
                update[i + 1] = page1;
                i = -1;
            }
        }
        return update;
    }
}