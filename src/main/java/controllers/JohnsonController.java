package controllers;

import algorithms.JohnsonAlgorithm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.FileParser;
import utils.FileSave;
import utils.RandomGenerator;
import utils.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JohnsonController {

    private HungarianController hungarianController;
    private GridPane gridPane;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField amountTF;
    private int tasksAmount;
    private List<Task> tasks;

    public JohnsonController() {
        hungarianController = new HungarianController();
        gridPane = hungarianController.getGridPane();
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        hungarianController.backToMenu(event);
    }

    @FXML
    void enterData() {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        tasksAmount = Integer.parseInt(amountTF.getText());
        hungarianController.displayMatrix(tasksAmount, 2, borderPane, gridPane);
        hungarianController.fillMatrix(RandomGenerator.generate(tasksAmount * 2), gridPane);
    }

    @FXML
    void openFile() {
        File file = hungarianController.getFileChooser().showOpenDialog(borderPane.getScene().getWindow());
        List<String> data = null;
        if (file != null) {
            data = FileParser.parseForKnapsackProblem(file.getPath());
        }

        displayOnScreen(data);
    }

    @FXML
    void saveFile() {
        List<Integer> toSave = new ArrayList<>(hungarianController.getNumbersFromScreen(gridPane));
        File file = hungarianController.getFileChooser().showSaveDialog(borderPane.getScene().getWindow());
        if (file != null) {
            FileSave.saveJonson(file.getPath(), toSave);
        }
    }

    @FXML
    void solve() {
        initializeTaskList();
        JohnsonAlgorithm johnsonAlgorithm = new JohnsonAlgorithm(tasks);
        int totalDuration = (int) johnsonAlgorithm.solve();
        displayResult(totalDuration, johnsonAlgorithm.getTasks());
    }

    private void initializeTaskList() {
        List<Integer> numbersFromScreen = hungarianController.getNumbersFromScreen(gridPane);
        tasks = new ArrayList<>();

        for (int i = 0; i < numbersFromScreen.size() / 2; i++) {
            int durationOnFirstMachine = numbersFromScreen.get(i);
            int durationOnSecondMachine = numbersFromScreen.get(i + tasksAmount);
            tasks.add(Task.of(i, durationOnFirstMachine, durationOnSecondMachine));
        }
    }

    private void displayOnScreen(List<String> data) {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        List<String> matrixNumbers = new ArrayList<>();
        matrixNumbers.addAll(Arrays.asList(data.get(0).split(" ")));
        matrixNumbers.addAll(Arrays.asList(data.get(1).split(" ")));

        hungarianController.displayMatrix(matrixNumbers.size() / 2, 2, borderPane, gridPane);
        hungarianController.fillMatrix(matrixNumbers, gridPane);
    }

    private void displayResult(int totalDuration, List<Task> tasks) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Label duration = new Label("Duration: " + String.valueOf(totalDuration));
        StringBuilder order = new StringBuilder();
        tasks.forEach(task -> order.append(task.getNumber()).append(" "));
        vBox.getChildren().addAll(duration, new Label("Order " + order.toString()));

        this.borderPane.setCenter(vBox);

    }
}