package main.java.day01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Day01Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day1part2data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            int[] row = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            leftList.add(row[0]);
            rightList.add(row[1]);
        }

        scanner.close();

        Map<Integer, Integer> numsAppearances = new HashMap<>();

        for (int num : rightList) {
            numsAppearances.putIfAbsent(num, 0);
            int newCount = numsAppearances.get(num) + 1;
            numsAppearances.put(num, newCount);
        }

        int similarityScore = 0;

        for (int num : leftList) {
            if (numsAppearances.containsKey(num)) {
                similarityScore += num * numsAppearances.get(num);
            }
        }

        System.out.println("Similarity score: " + similarityScore);
    }
}