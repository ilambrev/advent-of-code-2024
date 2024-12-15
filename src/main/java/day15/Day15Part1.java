package main.java.day15;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15Part1 {
    private static final char ROBOT = '@';
    private static final char WALL = '#';
    private static final char BOX = 'O';
    private static final char EMPTY_SPACE = '.';
    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';
    private static final long GPS_MULTIPLIER = 100L;

    private static final List<Character> robotMoves = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day15data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        char[][] warehouse = readFileInput(scanner);

        scanner.close();

        int[] robotCoordinates = getRobotCoordinates(warehouse);

        System.out.println("Warehouse initial state");
        printWarehouse(warehouse);

        if (isRobotInWarehouse(robotCoordinates)) {
            moveRobot(warehouse, robotCoordinates);
        }

        System.out.println("Warehouse final state");
        printWarehouse(warehouse);

        System.out.println("Sum of all boxes' GPS coordinates is: " +
                calculateSumOfBoxesGPSCoordinates(warehouse));
    }

    private static boolean isRobotInWarehouse(int[] coordinates) {
        return coordinates[0] > -1 && coordinates[1] > -1;
    }

    public static long calculateSumOfBoxesGPSCoordinates(char[][] warehouse) {
        int rows = warehouse.length - 2;
        int cols = warehouse[0].length - 2;
        long sumOfCoordinates = 0L;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (warehouse[i][j] == BOX) {
                    sumOfCoordinates += i * GPS_MULTIPLIER + j;
                }
            }
        }

        return sumOfCoordinates;
    }

    public static void moveRobot(char[][] warehouse, int[] robotCoordinates) {
        for (char step : robotMoves) {
            switch (step) {
                case UP -> moveUp(warehouse, robotCoordinates);
                case DOWN -> moveDown(warehouse, robotCoordinates);
                case LEFT -> moveLeft(warehouse, robotCoordinates);
                case RIGHT -> moveRight(warehouse, robotCoordinates);
            }
        }
    }

    public static void moveUp(char[][] warehouse, int[] robotCoordinates) {
        if (warehouse[robotCoordinates[0] - 1][robotCoordinates[1]] == BOX) {
            int row = robotCoordinates[0];
            int col = robotCoordinates[1];
            boolean isWallReached = false;
            while (warehouse[row][col] != EMPTY_SPACE) {
                if (warehouse[row][col] == WALL) {
                    isWallReached = true;
                    break;
                }
                row--;
            }
            if (!isWallReached) {
                warehouse[row][col] = BOX;
                warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
                warehouse[robotCoordinates[0] - 1][robotCoordinates[1]] = ROBOT;
                robotCoordinates[0] -= 1;
            }
        } else if (warehouse[robotCoordinates[0] - 1][robotCoordinates[1]] == EMPTY_SPACE) {
            warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
            warehouse[robotCoordinates[0] - 1][robotCoordinates[1]] = ROBOT;
            robotCoordinates[0] -= 1;
        }
    }

    public static void moveDown(char[][] warehouse, int[] robotCoordinates) {
        if (warehouse[robotCoordinates[0] + 1][robotCoordinates[1]] == BOX) {
            int row = robotCoordinates[0];
            int col = robotCoordinates[1];
            boolean isWallReached = false;
            while (warehouse[row][col] != EMPTY_SPACE) {
                if (warehouse[row][col] == WALL) {
                    isWallReached = true;
                    break;
                }
                row++;
            }
            if (!isWallReached) {
                warehouse[row][col] = BOX;
                warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
                warehouse[robotCoordinates[0] + 1][robotCoordinates[1]] = ROBOT;
                robotCoordinates[0] += 1;
            }
        } else if (warehouse[robotCoordinates[0] + 1][robotCoordinates[1]] == EMPTY_SPACE) {
            warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
            warehouse[robotCoordinates[0] + 1][robotCoordinates[1]] = ROBOT;
            robotCoordinates[0] += 1;
        }
    }

    public static void moveLeft(char[][] warehouse, int[] robotCoordinates) {
        if (warehouse[robotCoordinates[0]][robotCoordinates[1] - 1] == BOX) {
            int row = robotCoordinates[0];
            int col = robotCoordinates[1];
            boolean isWallReached = false;
            while (warehouse[row][col] != EMPTY_SPACE) {
                if (warehouse[row][col] == WALL) {
                    isWallReached = true;
                    break;
                }
                col--;
            }
            if (!isWallReached) {
                warehouse[row][col] = BOX;
                warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
                warehouse[robotCoordinates[0]][robotCoordinates[1] - 1] = ROBOT;
                robotCoordinates[1] -= 1;
            }
        } else if (warehouse[robotCoordinates[0]][robotCoordinates[1] - 1] == EMPTY_SPACE) {
            warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
            warehouse[robotCoordinates[0]][robotCoordinates[1] - 1] = ROBOT;
            robotCoordinates[1] -= 1;
        }
    }

    public static void moveRight(char[][] warehouse, int[] robotCoordinates) {
        if (warehouse[robotCoordinates[0]][robotCoordinates[1] + 1] == BOX) {
            int row = robotCoordinates[0];
            int col = robotCoordinates[1];
            boolean isWallReached = false;
            while (warehouse[row][col] != EMPTY_SPACE) {
                if (warehouse[row][col] == WALL) {
                    isWallReached = true;
                    break;
                }
                col++;
            }
            if (!isWallReached) {
                warehouse[row][col] = BOX;
                warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
                warehouse[robotCoordinates[0]][robotCoordinates[1] + 1] = ROBOT;
                robotCoordinates[1] += 1;
            }
        } else if (warehouse[robotCoordinates[0]][robotCoordinates[1] + 1] == EMPTY_SPACE) {
            warehouse[robotCoordinates[0]][robotCoordinates[1]] = EMPTY_SPACE;
            warehouse[robotCoordinates[0]][robotCoordinates[1] + 1] = ROBOT;
            robotCoordinates[1] += 1;
        }
    }

    public static int[] getRobotCoordinates(char[][] warehouse) {
        int[] robotCoordinates = new int[]{-1, -1};
        int rows = warehouse.length - 2;
        int cols = warehouse[0].length - 2;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (warehouse[i][j] == ROBOT) {
                    robotCoordinates[0] = i;
                    robotCoordinates[1] = j;

                    return robotCoordinates;
                }
            }
        }

        return robotCoordinates;
    }

    public static void printWarehouse(char[][] warehouse) {
        for (char[] row : warehouse) {
            String rowAsString = Stream.of(row)
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            System.out.println(rowAsString);
        }
        System.out.println();
    }

    public static char[][] readFileInput(Scanner scanner) {
        List<char[]> rows = new ArrayList<>();
        boolean isWarehouseBuilt = false;

        while (scanner.hasNextLine()) {
            String row = scanner.nextLine();
            if (row.isEmpty()) {
                isWarehouseBuilt = true;
                continue;
            }
            if (isWarehouseBuilt) {
                robotMoves.addAll(Arrays.stream(row.split(""))
                        .map(c -> c.charAt(0))
                        .toList());
            } else {
                rows.add(row.toCharArray());
            }
        }

        char[][] warehouse = new char[rows.size()][rows.get(0).length];

        for (int i = 0; i < rows.size(); i++) {
            warehouse[i] = rows.get(i);
        }

        return warehouse;
    }
}