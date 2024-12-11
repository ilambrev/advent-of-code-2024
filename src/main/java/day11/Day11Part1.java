package main.java.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day11Part1 {
    private static final int BLINKS = 25;
    private static final int STONE_NUMBER_MULTIPLIER = 2024;
    private static final long MIN_NEW_STONE_NUMBER = 1L;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/files/day11data.txt");
        Scanner scanner = new Scanner(file);

        List<Long> stones = Arrays.stream(scanner.nextLine().split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        for (int i = 1; i <= BLINKS; i++) {
            stones = blink(stones);
        }

        System.out.println("Number of stones after 25 blinks: " + stones.size());
    }

    public static List<Long> blink(List<Long> stones) {
        List<Long> newStonesOrder = new ArrayList<>();

        for (int i = 0; i < stones.size(); i++) {
            if (stones.get(i) == 0) {
                newStonesOrder.add(MIN_NEW_STONE_NUMBER);
            } else if (String.valueOf(stones.get(i)).length() % 2 == 0) {
                String stoneNum = String.valueOf(stones.get(i));
                long leftStoneHalf = Long.parseLong(stoneNum.substring(0, stoneNum.length() / 2));
                long rightStoneHalf = Long.parseLong(stoneNum.substring((stoneNum.length() / 2)));
                newStonesOrder.add(leftStoneHalf);
                newStonesOrder.add(rightStoneHalf);
            } else {
                long newStoneNumber = stones.get(i) * STONE_NUMBER_MULTIPLIER;
                newStonesOrder.add(newStoneNumber);
            }
        }

        return newStonesOrder;
    }
}