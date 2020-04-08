package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {

    public static Matrix parseForHungarianMethod(String path) {
        Matrix matrix = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            int size = Integer.parseInt(bufferedReader.readLine());

            Object[] objects = bufferedReader.lines().toArray();
            String[] fileLines = Arrays.copyOf(objects, objects.length, String[].class);
            matrix = new Matrix(size);
            for (int i = 0; i < fileLines.length; i++) {

                int[] matrixRows = Arrays.stream(fileLines[i].split(" ")).mapToInt(Integer::valueOf).toArray();

                matrix.getMatrixElements()[i] = matrixRows;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    public static List<String> parseForKnapsackProblem(String path) {
        List<String> data = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            data = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static List<String> parseForTSP(String path) {
        List<String> data = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            data = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}