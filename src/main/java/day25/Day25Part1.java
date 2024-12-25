package main.java.day25;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day25Part1 {
    private static final char FILLED_FIELD = '#';
    private static final char EMPTY_FIELD = '.';
    private static final List<int[]> locks = new ArrayList<>();
    private static final List<int[]> keys = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day25data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        readFileInput(scanner);

        scanner.close();

        int matches = 0;

        for (int[] lock : locks) {
            for (int[] key : keys) {
                if (isKeyFeatInLock(lock, key)) {
                    matches++;
                }
            }
        }

        System.out.println("Number of unique lock/key pairs that fit together: " + matches);
    }

    public static boolean isKeyFeatInLock(int[] lock, int[] key) {
        for (int i = 0; i < lock.length; i++) {
            if (lock[i] + key[i] > 5) {
                return false;
            }
        }

        return true;
    }

    public static int[] getLock(char[][] schematic) {
        int[] lock = new int[schematic[0].length];
        for (int i = 0; i < schematic[0].length; i++) {
            int counter = 0;
            for (int j = 1; j < schematic.length - 1; j++) {
                if (schematic[j][i] == EMPTY_FIELD) {
                    lock[i] = counter;
                    break;
                }
                counter++;
            }
            lock[i] = counter;
        }

        return lock;
    }

    public static int[] getKey(char[][] schematic) {
        int[] key = new int[schematic[0].length];
        for (int i = 0; i < schematic[0].length; i++) {
            int counter = 0;
            for (int j = schematic.length - 2; j >= 1; j--) {
                if (schematic[j][i] == EMPTY_FIELD) {
                    key[i] = counter;
                    break;
                }
                counter++;
            }
            key[i] = counter;
        }
        
        return key;
    }

    public static char[][] convertListToArray(List<char[]> rows) {
        char[][] arr = new char[rows.size()][rows.get(0).length];
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                arr[i][j] = rows.get(i)[j];
            }
        }

        return arr;
    }

    public static void readFileInput(Scanner scanner) {
        List<char[]> rows = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                char[] row = line.toCharArray();
                rows.add(row);
            }
            if (line.isEmpty() || !scanner.hasNextLine()) {
                char[][] schematic = convertListToArray(rows);
                String firstRow = new String(schematic[0]);
                if (firstRow.equals(String.valueOf(FILLED_FIELD).repeat(schematic[0].length))) {
                    int[] lock = getLock(schematic);
                    locks.add(lock);
                } else {
                    int[] key = getKey(schematic);
                    keys.add(key);
                }
                rows.clear();
            }
        }
    }
}