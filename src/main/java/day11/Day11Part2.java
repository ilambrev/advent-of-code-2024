package main.java.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11Part2 {
    private static final int BLINKS = 75;
    private static final int STONE_NUMBER_MULTIPLIER = 2024;
    private static final long MIN_NEW_STONE_NUMBER = 1L;
    private static final Map<Long, long[]> numParts = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/files/day11data.txt");
        Scanner scanner = new Scanner(file);

        HashMap<Long, Long> stones1 = getStones(scanner);
        HashMap<Long, Long> stones2 = new HashMap<>();

        scanner.close();

        for (int i = 1; i <= BLINKS; i++) {
            if (i % 2 != 0) {
                stones2.clear();
                blink(stones1, stones2);
            } else {
                stones1.clear();
                blink(stones2, stones1);
            }
        }

        long stonesNumber = BLINKS % 2 != 0 ? countStones(stones2) : countStones(stones1);

        System.out.println("Number of stones after " + BLINKS + " blinks: " + stonesNumber);
    }

    public static long countStones(HashMap<Long, Long> stones) {
        return stones.values().stream().reduce(0L, Long::sum);
    }

    public static void blink(HashMap<Long, Long> fullMap, HashMap<Long, Long> emptyMap) {
        for (Map.Entry<Long, Long> entry : fullMap.entrySet()) {
            long currentStone = entry.getKey();
            if (numParts.containsKey(currentStone)) {
                long[] resultStone = numParts.get(currentStone);
                for (long stone : resultStone) {
                    emptyMap.putIfAbsent(stone, 0L);
                    emptyMap.put(stone, emptyMap.get(stone) + fullMap.get(currentStone));
                }
            } else {
                if (currentStone == 0) {
                    numParts.put(currentStone, new long[]{MIN_NEW_STONE_NUMBER});
                    emptyMap.putIfAbsent(MIN_NEW_STONE_NUMBER, 0L);
                    emptyMap.put(MIN_NEW_STONE_NUMBER, emptyMap.get(MIN_NEW_STONE_NUMBER) + fullMap.get(currentStone));
                } else if (String.valueOf(currentStone).length() % 2 == 0) {
                    String stoneNum = String.valueOf(currentStone);
                    long leftStoneHalf = Long.parseLong(stoneNum.substring(0, stoneNum.length() / 2));
                    long rightStoneHalf = Long.parseLong(stoneNum.substring((stoneNum.length() / 2)));
                    numParts.put(currentStone, new long[]{leftStoneHalf, rightStoneHalf});
                    emptyMap.putIfAbsent(leftStoneHalf, 0L);
                    emptyMap.put(leftStoneHalf, emptyMap.get(leftStoneHalf) + fullMap.get(currentStone));
                    emptyMap.putIfAbsent(rightStoneHalf, 0L);
                    emptyMap.put(rightStoneHalf, emptyMap.get(rightStoneHalf) + fullMap.get(currentStone));
                } else {
                    long newStoneNumber = currentStone * STONE_NUMBER_MULTIPLIER;
                    numParts.put(currentStone, new long[]{newStoneNumber});
                    emptyMap.putIfAbsent(newStoneNumber, 0L);
                    emptyMap.put(newStoneNumber, emptyMap.get(newStoneNumber) + fullMap.get(currentStone));
                }
            }
        }
    }

    public static HashMap<Long, Long> getStones(Scanner scanner) {
        HashMap<Long, Long> stones = new HashMap<>();
        long[] inputData = Arrays.stream(scanner.nextLine().split(" "))
                .mapToLong(Long::parseLong)
                .toArray();

        for (long num : inputData) {
            if (!stones.containsKey(num)) {
                stones.put(num, 0L);
            }
            stones.put(num, stones.get(num) + 1);
        }

        return stones;
    }
}