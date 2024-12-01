package main.java.day01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Day01Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day1part1data.txt");
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

        Collections.sort(leftList);
        Collections.sort(rightList);

        int totalDistance = 0;

        for (int i = 0; i < leftList.size(); i++) {
            int currentDistance = Math.abs(Math.max(leftList.get(i), rightList.get(i)) - Math.min(leftList.get(i), rightList.get(i)));
            totalDistance += currentDistance;
        }

        System.out.println("Total distance: " + totalDistance);
    }
}