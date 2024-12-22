package main.java.day22;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day22Part1 {
    private static final int NUM_OF_GENERATED_SECRET_NUMBERS = 2000;
    private static final int FIRST_MULTIPLAYER = 64;
    private static final int SECOND_MULTIPLAYER = 2048;
    private static final int DIVISOR = 32;
    private static final int MODULO = 16777216;

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day22data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        List<Long> secretNumbers = readFileInput(scanner);

        scanner.close();

        for (int i = 0; i < secretNumbers.size(); i++) {
            long buyerNum = secretNumbers.get(i);
            for (int j = 1; j <= NUM_OF_GENERATED_SECRET_NUMBERS; j++) {
                buyerNum = createNewSecretNum(buyerNum);

            }
            secretNumbers.set(i, buyerNum);
        }

        System.out.println("Sum of the 2000th secret number generated by each buyer: " +
                secretNumbers.stream().mapToLong(Long::longValue).sum());
    }

    public static long createNewSecretNum(long secretNum) {
        long result = multiplyNum(secretNum, FIRST_MULTIPLAYER);
        secretNum = mixNum(result, secretNum);
        secretNum = pruneNum(secretNum);
        result = divideNum(secretNum, DIVISOR);
        secretNum = mixNum(result, secretNum);
        secretNum = pruneNum(secretNum);
        result = multiplyNum(secretNum, SECOND_MULTIPLAYER);
        secretNum = mixNum(result, secretNum);
        secretNum = pruneNum(secretNum);

        return secretNum;
    }

    public static long multiplyNum(long secretNum, int multiplayer) {
        return secretNum * multiplayer;
    }

    public static long divideNum(long secretNum, int divisor) {
        return secretNum / divisor;
    }

    public static long mixNum(long value, long secretNum) {
        return value ^ secretNum;
    }

    public static long pruneNum(long secretNum) {
        return secretNum % MODULO;
    }

    public static List<Long> readFileInput(Scanner scanner) {
        List<Long> initialSecretNumbers = new ArrayList<>();

        while (scanner.hasNextLong()) {
            initialSecretNumbers.add(scanner.nextLong());
        }

        return initialSecretNumbers;
    }
}