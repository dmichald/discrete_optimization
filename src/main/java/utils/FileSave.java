package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileSave {
    public static void saveHungarianMethod(String path, List<Integer> toSave) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {

            bufferedWriter.write(String.format("%d", (int) Math.sqrt(toSave.size())) + "\n");
            for (int i = 0; i < toSave.size(); i++) {
                if (i % Math.sqrt(toSave.size()) == 0 && i != 0) {
                    bufferedWriter.write("\n");
                }
                bufferedWriter.write(toSave.get(i).toString() + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveKnapsackProblem(String path, List<Integer> toSave) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(toSave.get(0) + "\n");
            bufferedWriter.write(toSave.get(1) + "\n");
            for (int i = 2; i < toSave.size(); i++) {
                if (i == 2 + toSave.get(0)) {
                    bufferedWriter.write("\n");
                }
                bufferedWriter.write(toSave.get(i).toString() + " ");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveTSP(String path, List<Integer> toSave) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < toSave.size(); i++) {
                if (i % 2 == 0 && i != 0) {
                    bufferedWriter.write("\n");
                }
                bufferedWriter.write(toSave.get(i) + " ");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJonson(String path, List<Integer> toSave) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < toSave.size(); i++) {
                if (i == toSave.size() / 2) {
                    bufferedWriter.write("\n");
                }
                bufferedWriter.write(toSave.get(i) + " ");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
