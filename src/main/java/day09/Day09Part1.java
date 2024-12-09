package main.java.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day09Part1 {
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
        String arrangedFilesRepresentation = removeFreeSpaceBetweenFiles(filesRepresentation);
        long checksum = calculateChecksum(arrangedFilesRepresentation);

        System.out.println("File checksum: " + checksum);
    }

    public static long calculateChecksum(String arrangedFilesRepresentation) {
        long sum = 0L;
        for (int i = 0; i < arrangedFilesRepresentation.length(); i++) {
            if (idsMap.containsKey(i)) {
                sum += idsMap.get(i) * i;
            }
        }

        return sum;
    }

    public static String removeFreeSpaceBetweenFiles(String filesRepresentation) {
        StringBuilder arrangedFilesRepresentation = new StringBuilder();
        StringBuilder freeSpaces = new StringBuilder();
        int index = filesRepresentation.length() - 1;

        for (int i = 0; i < filesRepresentation.length(); i++) {
            while (String.valueOf(filesRepresentation.charAt(index)).equals(FREE_SPACE_SIGN) && index > i) {
                freeSpaces.append(FREE_SPACE_SIGN);
                index--;
            }
            if (i == index + 1) {
                break;
            }
            if (String.valueOf(filesRepresentation.charAt(i)).equals(FREE_SPACE_SIGN)) {
                freeSpaces.append(FREE_SPACE_SIGN);
                arrangedFilesRepresentation.append(filesRepresentation.charAt(index));
                int id = idsMap.get(index);
                idsMap.remove(index);
                idsMap.put(i, id);
                index--;
                while (String.valueOf(filesRepresentation.charAt(index)).equals(FREE_SPACE_SIGN) && index > i) {
                    freeSpaces.append(FREE_SPACE_SIGN);
                    index--;
                }
            } else {
                arrangedFilesRepresentation.append(filesRepresentation.charAt(i));
            }
        }

        arrangedFilesRepresentation.append(freeSpaces);

        return arrangedFilesRepresentation.toString();
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