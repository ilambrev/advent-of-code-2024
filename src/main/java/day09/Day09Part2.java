package main.java.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day09Part2 {
    private static final String FREE_SPACE_SIGN = ".";
    private static final String FILE_BLOCK = "1";
    private static final Map<Integer, Integer> idsMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/files/day09data.txt");
        Scanner scanner = new Scanner(file);
        StringBuilder diskMap = new StringBuilder();

        while (scanner.hasNextLine()) {
            diskMap.append(scanner.nextLine());
        }

        scanner.close();

        String filesRepresentation = getFilesRepresentation(diskMap.toString());
        String[] arrangedFilesRepresentation = removeFreeSpaceBetweenFiles(filesRepresentation);
        long checksum = calculateChecksum(arrangedFilesRepresentation);

        System.out.println("File checksum: " + checksum);
    }

    public static long calculateChecksum(String[] arrangedFilesRepresentation) {
        long sum = 0L;
        for (int i = 0; i < arrangedFilesRepresentation.length; i++) {
            if (idsMap.containsKey(i)) {
                sum += idsMap.get(i) * i;
            }
        }

        return sum;
    }

    public static String[] removeFreeSpaceBetweenFiles(String filesRepresentation) {
        String[] arrangedFilesRepresentation = filesRepresentation.split("");

        int index = arrangedFilesRepresentation.length - 1;

        while (index > 0) {
            int[] fileBorders = findFileBorders(arrangedFilesRepresentation, index);
            int fileSize = fileBorders[1] == fileBorders[0] ? 1 : (fileBorders[1] - fileBorders[0]) + 1;
            String freeSpaceNeeded = FREE_SPACE_SIGN.repeat(fileSize);
            int freeSpaceIndex = findEmptySpace(arrangedFilesRepresentation, fileSize, fileBorders[0]);

            if (freeSpaceIndex > -1 && freeSpaceIndex < fileBorders[0]) {
                int fileId = idsMap.get(fileBorders[0]);
                for (int i = freeSpaceIndex; i < freeSpaceIndex + freeSpaceNeeded.length(); i++) {
                    arrangedFilesRepresentation[i] = FILE_BLOCK;
                    idsMap.put(i, fileId);
                }
                for (int i = fileBorders[0]; i <= fileBorders[1]; i++) {
                    arrangedFilesRepresentation[i] = FREE_SPACE_SIGN;
                    idsMap.remove(i);
                }
            }

            index = Math.max(fileBorders[0] - 1, 0);
        }

        return arrangedFilesRepresentation;
    }

    public static int findEmptySpace(String[] arrangedFilesRepresentation, int requiredSize, int endIndex) {
        int index = -1;
        int emptySpaceSize = 0;

        for (int i = 0; i < endIndex; i++) {
            if (arrangedFilesRepresentation[i].equals(FREE_SPACE_SIGN)) {
                emptySpaceSize++;
            } else {
                emptySpaceSize = 0;
            }
            if (emptySpaceSize == requiredSize) {
                index = i - requiredSize + 1;
                break;
            }
        }

        return index;
    }

    public static int[] findFileBorders(String[] arrangedFilesRepresentation, int index) {
        int[] fileBorders = new int[2];

        while (arrangedFilesRepresentation[index].equals(FREE_SPACE_SIGN)) {
            if (index == 0) {
                break;
            }
            index--;
        }

        fileBorders[1] = index;
        int fileId = idsMap.get(index);

        for (int i = index - 1; i >= 0; i--) {
            if (arrangedFilesRepresentation[index].equals(FREE_SPACE_SIGN) ||
                    idsMap.get(i) == null || idsMap.get(i) != fileId) {
                fileBorders[0] = index;
                break;
            }
            index--;
        }

        return fileBorders;
    }

    public static String getFilesRepresentation(String diskMap) {
        StringBuilder representation = new StringBuilder();
        for (int i = 0; i < diskMap.length(); i++) {
            int num = Integer.parseInt(String.valueOf(diskMap.charAt(i)));
            if (i % 2 == 0) {
                int fileId = i / 2;
                for (int j = representation.length(); j < representation.length() + num; j++) {
                    idsMap.put(j, fileId);
                }
                representation.append(FILE_BLOCK.repeat(num));
            } else if (num > 0) {
                representation.append(FREE_SPACE_SIGN.repeat(num));
            }
        }

        return representation.toString();
    }
}