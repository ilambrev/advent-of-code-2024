package main.java.day12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/files/day12data.txt");
        Scanner scanner = new Scanner(fileInputStream);

        char[][] garden = readFileInput(scanner);

        scanner.close();

        Map<Character, List<int[]>> types = getTypes(garden);

        int totalPrice = 0;

        for (Map.Entry<Character, List<int[]>> type : types.entrySet()) {
            totalPrice += getPlantRegionsPrice(type.getKey(), type.getValue(), garden);
        }

        System.out.println("The total price of fencing all regions: " + totalPrice);
    }

    private static void getRegionCoordinates(Set<int[]> coordinates, int row, int col,
                                             char plantType, char[][] garden) {
        coordinates.add(new int[]{row, col});
        garden[row][col] = (char) (plantType + 32);
        
        if ((col + 1 < garden[0].length) && (garden[row][col + 1] == plantType)) {
            getRegionCoordinates(coordinates, row, col + 1, plantType, garden);
        }
        if ((col - 1 >= 0) && (garden[row][col - 1] == plantType)) {
            getRegionCoordinates(coordinates, row, col - 1, plantType, garden);
        }
        if ((row + 1 < garden.length) && (garden[row + 1][col] == plantType)) {
            getRegionCoordinates(coordinates, row + 1, col, plantType, garden);
        }
        if ((row - 1 >= 0) && (garden[row - 1][col] == plantType)) {
            getRegionCoordinates(coordinates, row - 1, col, plantType, garden);
        }
    }

    public static int getPlantRegionsPrice(char plantType, List<int[]> coordinates, char[][] garden) {
        Set<int[]> checkedPlots = new HashSet<>();
        int price = 0;
        for (int[] coordinate : coordinates) {
            if (checkedPlots.stream()
                    .filter(p -> p[0] == coordinate[0] && p[1] == coordinate[1])
                    .collect(Collectors.toSet()).isEmpty()) {

                Set<int[]> regionCoordinates = new HashSet<>();
                int upSideFence = 0;
                int downSideFence = 0;
                int leftSideFence = 0;
                int rightSideFence = 0;
                char newPlantType = (char) (plantType + 32);

                getRegionCoordinates(regionCoordinates, coordinate[0], coordinate[1], plantType, garden);
                checkedPlots.addAll(regionCoordinates);

                for (int[] regionCoordinate : regionCoordinates) {
                    int row = regionCoordinate[0];
                    int col = regionCoordinate[1];

                    if ((row == 0) || (garden[row - 1][col] != newPlantType)) {
                        upSideFence++;
                    }
                    if ((row == garden.length - 1) || (garden[row + 1][col] != newPlantType)) {
                        downSideFence++;
                    }
                    if ((col == 0) || (garden[row][col - 1] != newPlantType)) {
                        leftSideFence++;
                    }
                    if ((col == garden[0].length - 1) || (garden[row][col + 1] != newPlantType)) {
                        rightSideFence++;
                    }
                }
                int regionPerimeter = (upSideFence + downSideFence + leftSideFence + rightSideFence);
                price += regionCoordinates.size() * regionPerimeter;
            }
        }

        return price;
    }

    public static Map<Character, List<int[]>> getTypes(char[][] garden) {
        Map<Character, List<int[]>> types = new HashMap<>();
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[i].length; j++) {
                types.putIfAbsent(garden[i][j], new ArrayList<>());
                types.get(garden[i][j]).add(new int[]{i, j});
            }
        }
        return types;
    }

    public static char[][] readFileInput(Scanner scanner) {
        List<char[]> rows = new ArrayList<>();

        while (scanner.hasNextLine()) {
            char[] row = scanner.nextLine().toCharArray();
            rows.add(row);
        }

        char[][] garden = new char[rows.size()][rows.get(0).length];

        for (int i = 0; i < rows.size(); i++) {
            garden[i] = rows.get(i);
        }

        return garden;
    }
}