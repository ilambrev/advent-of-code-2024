package main.java.day07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Day07Part1 {
    private static final char ADD = '+';
    private static final char MULTIPLY = '*';
    static List<char[]> combinations = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day07data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        List<long[]> calibrationEquations = readFileInput(scanner);

        scanner.close();

        Map<Integer, char[][]> operatorsCombinationsMap = new HashMap<>();
        long totalCalibrationResult = 0L;

        for (long[] equation : calibrationEquations) {
            int numberOfOperations = equation.length - 2;

            char[][] operatorsCombinations;

            if (operatorsCombinationsMap.containsKey(numberOfOperations)) {
                operatorsCombinations = operatorsCombinationsMap.get(numberOfOperations);
            } else {
                combinations.clear();
                generateOperatorsCombinations("", numberOfOperations);
                operatorsCombinations = combinations.toArray(new char[combinations.size()][]);
                operatorsCombinationsMap.put(numberOfOperations, operatorsCombinations);
            }

            if (isEquationTrue(equation, operatorsCombinations)) {
                totalCalibrationResult += equation[0];
            }
        }

        System.out.println("Total calibration result: " + totalCalibrationResult);
    }

    public static boolean isEquationTrue(long[] equation, char[][] operatorsCombinations) {
        long testValue = equation[0];

        for (char[] combination : operatorsCombinations) {
            long value = equation[1];
            for (int i = 0; i < combination.length; i++) {
                if (combination[i] == ADD) {
                    value = value + equation[i + 2];
                } else {
                    value = value * equation[i + 2];
                }
                if (value > testValue) {
                    break;
                }
            }
            if (value == testValue) {
                return true;
            }
        }

        return false;
    }

    static void generateOperatorsCombinations(String prefix, int numberOfOperations) {
        char[] operationsTypes = {ADD, MULTIPLY};
        if (numberOfOperations == 0) {
            char[] combination = prefix.toCharArray();
            combinations.add(combination);
            return;
        }

        for (int i = 0; i < operationsTypes.length; ++i) {
            String newPrefix = prefix + operationsTypes[i];
            generateOperatorsCombinations(newPrefix, numberOfOperations - 1);
        }
    }

    public static List<long[]> readFileInput(Scanner scanner) {
        List<long[]> calibrationEquations = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] equationParts = scanner.nextLine().split(":\\s+");
            long[] operators = Arrays.stream(equationParts[1].split("\\s+")).mapToLong(Long::parseLong).toArray();
            long[] equation = new long[operators.length + 1];
            equation[0] = Long.parseLong(equationParts[0]);
            System.arraycopy(operators, 0, equation, 1, operators.length);
            calibrationEquations.add(equation);
        }

        return calibrationEquations;
    }
}