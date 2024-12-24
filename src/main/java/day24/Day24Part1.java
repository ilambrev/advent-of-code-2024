package main.java.day24;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Day24Part1 {
    private static final Map<String, Boolean> wiresStates = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day24data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        Deque<String> operations = readFileInput(scanner);

        scanner.close();

        processOperations(operations);

        List<String> zWires = wiresStates.keySet()
                .stream()
                .filter(aBoolean -> aBoolean.contains("z"))
                .sorted()
                .toList();

        StringBuilder binaryResult = new StringBuilder();

        for (int i = zWires.size() - 1; i >= 0; i--) {
            if (wiresStates.get(zWires.get(i))) {
                binaryResult.append("1");
            } else {
                binaryResult.append("0");
            }
        }

        System.out.println("Decimal number: " + Long.parseLong(binaryResult.toString(), 2));
    }

    public static void processOperations(Deque<String> operations) {
        while (!operations.isEmpty()) {
            String operation = operations.pop();
            String[] operationData = operation.split(" -> ");
            String leftPart = operationData[0];
            String result = operationData[1];
            String[] operands = leftPart.split(" ");
            String firstOperand = operands[0];
            String secondOperand = operands[2];
            String operationType = operands[1];
            if (wiresStates.containsKey(firstOperand) && wiresStates.containsKey(secondOperand)) {
                boolean resultValue = switch (operationType) {
                    case "AND" -> wiresStates.get(firstOperand) && wiresStates.get(secondOperand);
                    case "OR" -> wiresStates.get(firstOperand) || wiresStates.get(secondOperand);
                    case "XOR" -> wiresStates.get(firstOperand) ^ wiresStates.get(secondOperand);
                    default -> false;
                };
                wiresStates.put(result, resultValue);
            } else {
                operations.offer(operation);
            }
        }
    }

    public static Deque<String> readFileInput(Scanner scanner) {
        Deque<String> operations = new ArrayDeque<>();
        boolean isWiresInitialValuesEnd = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                isWiresInitialValuesEnd = true;
                continue;
            }
            if (isWiresInitialValuesEnd) {
                operations.add(line);
            } else {
                String[] wireData = line.split(": ");
                String wireName = wireData[0];
                boolean wireState = Integer.parseInt(wireData[1]) == 1;
                wiresStates.put(wireName, wireState);
            }
        }

        return operations;
    }
}