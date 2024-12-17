package main.java.day17;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day17Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day17data.txt");
        Scanner scanner = new Scanner(fileInputStream);
        Map<Character, Integer> registers = new HashMap<>();
        List<Integer> program = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty()) {
                String[] lineParts = line.split(": ");
                if (lineParts[0].equals("Program")) {
                    Arrays.stream(lineParts[1].split(","))
                            .forEach(n -> program.add(Integer.parseInt(n)));
                } else {
                    char register = lineParts[0].charAt(lineParts[0].length() - 1);
                    int value = Integer.parseInt(lineParts[1]);
                    registers.put(register, value);
                }
            }
        }

        scanner.close();

        int instructionPointer = 0;
        boolean isComputerHalted = false;

        while (!isComputerHalted) {
            int opcode = program.get(instructionPointer);
            int operand = program.get(instructionPointer + 1);
            boolean isJumpMade = false;

            if (operand == 4) {
                operand = registers.get('A');
            } else if (operand == 5) {
                operand = registers.get('B');
            } else if (operand == 6) {
                operand = registers.get('C');
            }

            switch (opcode) {
                case 0:
                    adv(registers, operand);
                    break;
                case 1:
                    bxl(registers, operand);
                    break;
                case 2:
                    bst(registers, operand);
                    break;
                case 3:
                    if (jnz(registers)) {
                        instructionPointer = operand;
                        isJumpMade = true;
                    }
                    break;
                case 4:
                    bxc(registers);
                    break;
                case 5:
                    result.add(out(operand));
                    break;
                case 6:
                    bdv(registers, operand);
                    break;
                case 7:
                    cdv(registers, operand);
                    break;
            }
            if (!isJumpMade) {
                instructionPointer += 2;
            }

            if (instructionPointer > program.size() - 1) {
                isComputerHalted = true;
            }
        }

        System.out.println("Result of program execution: " +
                result.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    public static int getFirstThreeBitsOfNum(int num) {
        String binaryString = Integer.toBinaryString(num);
        int beginIndex = 0;
        if (binaryString.length() > 2) {
            beginIndex = binaryString.length() - 3;
        }
        return Integer.parseInt(binaryString.substring(beginIndex), 2);
    }

    public static void adv(Map<Character, Integer> registers, int operand) {
        int numerator = registers.get('A');
        double denominator = Math.pow(2, operand);
        int result = (int) (numerator / denominator);
        registers.put('A', result);
    }

    public static void bxl(Map<Character, Integer> registers, int operand) {
        int registerValue = registers.get('B');
        int result = registerValue ^ operand;
        registers.put('B', result);
    }

    public static void bst(Map<Character, Integer> registers, int operand) {
        int result = operand % 8;
        registers.put('B', getFirstThreeBitsOfNum(result));
    }

    public static boolean jnz(Map<Character, Integer> registers) {
        return registers.get('A') != 0;
    }

    public static void bxc(Map<Character, Integer> registers) {
        int result = registers.get('B') ^ registers.get('C');
        registers.put('B', result);
    }

    public static int out(int operand) {
        return operand % 8;
    }

    public static void bdv(Map<Character, Integer> registers, int operand) {
        int numerator = registers.get('A');
        double denominator = Math.pow(2, operand);
        int result = (int) (numerator / denominator);
        registers.put('B', result);
    }

    public static void cdv(Map<Character, Integer> registers, int operand) {
        int numerator = registers.get('A');
        double denominator = Math.pow(2, operand);
        int result = (int) (numerator / denominator);
        registers.put('C', result);
    }
}