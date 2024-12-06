package main.java.day06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06Part1 {
    private static final char GUARD = '^';
    private static final char OBSTRUCTION = '#';
    private static final char EMPTY_SPACE = '.';
    private static final char VISITED_POSITION = 'X';

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day06data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        char[][] map = readFileInput(scanner);

        scanner.close();

        int[] guardPosition = findGuardPosition(map);

        int height = map.length;
        int width = map[0].length;
        char direction = 'u';
        int row = guardPosition[0];
        int col = guardPosition[1];

        while (row >= 0 && row < height && col >= 0 && col < width) {
            int oldRow = row;
            int oldCol = col;
            int[] newPosition = makeMove(row, col, direction);
            row = newPosition[0];
            col = newPosition[1];

            if (row < 0 || row == height || col < 0 || col == width) {
                map[oldRow][oldCol] = VISITED_POSITION;
                break;
            }

            if (map[row][col] == EMPTY_SPACE || map[row][col] == VISITED_POSITION) {
                map[row][col] = GUARD;
                map[oldRow][oldCol] = VISITED_POSITION;
            } else if (map[row][col] == OBSTRUCTION) {
                direction = changeDirection(direction);
                row = oldRow;
                col = oldCol;
            }
        }

        System.out.println("Distinct positions visited by guard: " + countGuardSteps(map));
    }

    public static char changeDirection(char direction) {
        if (direction == 'u') {
            return 'r';
        } else if (direction == 'r') {
            return 'd';
        } else if (direction == 'd') {
            return 'l';
        } else {
            return 'u';
        }
    }

    public static int[] makeMove(int row, int col, char direction) {
        switch (direction) {
            case 'u':
                row--;
                break;
            case 'r':
                col++;
                break;
            case 'd':
                row++;
                break;
            case 'l':
                col--;
                break;
        }
        return new int[]{row, col};
    }

    public static int countGuardSteps(char[][] map) {
        int steps = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == VISITED_POSITION) {
                    steps++;
                }
            }
        }
        return steps;
    }

    public static int[] findGuardPosition(char[][] map) {
        int[] guardPosition = new int[2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == GUARD) {
                    guardPosition[0] = i;
                    guardPosition[1] = j;
                }
            }
        }
        return guardPosition;
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