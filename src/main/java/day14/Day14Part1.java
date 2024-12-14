package main.java.day14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14Part1 {
    private static final int ROWS = 103;
    private static final int COLS = 101;
    private static final int SECONDS_TO_MOVE = 100;
    private static final int EMPTY_SPACE = 0;
    private static final List<int[]> robotsData = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day14data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        int[][] robotsSpace = readFileInput(scanner);

        scanner.close();

        printSpace(robotsSpace);

        for (int i = 1; i <= SECONDS_TO_MOVE; i++) {
            moveAllRobots(robotsSpace);
        }

        printSpace(robotsSpace);

        System.out.println("Safety factor after " + SECONDS_TO_MOVE +
                " seconds: " + calculateSafetyFactor(robotsSpace));
    }

    public static int calculateSafetyFactor(int[][] robotSpace) {
        int factor = 0;
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                factor = countRobotsInQuadrant(robotSpace, i);
            } else {
                factor *= countRobotsInQuadrant(robotSpace, i);
            }
        }

        return factor;
    }

    public static int countRobotsInQuadrant(int[][] robotSpace, int quadrantId) {
        int quadrantRows = ROWS / 2;
        int quadrantCols = COLS / 2;
        int quadrantStartRow = 0;
        int quadrantEndRow = quadrantRows;
        int quadrantStartCol = 0;
        int quadrantEndCol = quadrantCols;

        if (quadrantId == 1) {
            quadrantStartCol = quadrantCols + 1;
            quadrantEndCol = COLS;
        } else if (quadrantId == 3) {
            quadrantStartRow = quadrantRows + 1;
            quadrantEndRow = ROWS;
        } else if (quadrantId == 4) {
            quadrantStartCol = quadrantCols + 1;
            quadrantEndCol = COLS;
            quadrantStartRow = quadrantRows + 1;
            quadrantEndRow = ROWS;
        }

        int robotsInQuadrant = 0;

        for (int i = quadrantStartRow; i < quadrantEndRow; i++) {
            for (int j = quadrantStartCol; j < quadrantEndCol; j++) {
                robotsInQuadrant += robotSpace[i][j];
            }
        }

        return robotsInQuadrant;
    }

    public static void moveAllRobots(int[][] robotsSpace) {
        for (int i = 0; i < robotsData.size(); i++) {
            moveRobot(robotsSpace, i);
        }
    }

    public static void moveRobot(int[][] robotsSpace, int robotId) {
        int[] robotParams = robotsData.get(robotId);
        int robotRowPosition = robotParams[1];
        int robotColPosition = robotParams[0];
        int moveRows = robotParams[3];
        int moveCols = robotParams[2];

        robotsSpace[robotRowPosition][robotColPosition] -= 1;

        robotRowPosition += moveRows;
        robotColPosition += moveCols;

        if (robotRowPosition < 0) {
            robotRowPosition = ROWS + robotRowPosition;
        } else if (robotRowPosition > ROWS - 1) {
            robotRowPosition = robotRowPosition - ROWS;
        }

        if (robotColPosition < 0) {
            robotColPosition = COLS + robotColPosition;
        } else if (robotColPosition > COLS - 1) {
            robotColPosition = robotColPosition - COLS;
        }

        robotsSpace[robotRowPosition][robotColPosition] += 1;
        robotsData.get(robotId)[0] = robotColPosition;
        robotsData.get(robotId)[1] = robotRowPosition;
    }

    public static void printSpace(int[][] robotsSpace) {
        for (int[] row : robotsSpace) {
            System.out.println(Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")));
        }
        System.out.println();
    }

    public static int[][] readFileInput(Scanner scanner) {
        Pattern numPattern = Pattern.compile("-?[0-9]+");
        int[][] robotsSpace = new int[ROWS][COLS];

        while (scanner.hasNextLine()) {
            String robotData = scanner.nextLine();
            Matcher matcher = numPattern.matcher(robotData);
            int[] robotParams = matcher
                    .results()
                    .map(MatchResult::group)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            robotsData.add(robotParams);
            int robotRowPosition = robotParams[1];
            int robotColPosition = robotParams[0];

            if (robotsSpace[robotRowPosition][robotColPosition] == EMPTY_SPACE) {
                robotsSpace[robotRowPosition][robotColPosition] = 1;
            } else {
                int numOfRobots = Integer.parseInt(String.valueOf(robotsSpace[robotRowPosition][robotColPosition]));
                robotsSpace[robotRowPosition][robotColPosition] = numOfRobots + 1;
            }
        }

        return robotsSpace;
    }
}