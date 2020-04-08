package controllers;

import algorithms.KnapsackProblem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnapsackController {
    private GridPane gridPane;
    private HungarianController hungarianController;
    private ArrayList<Item> items;
    private int amountOfItems;
    private int backPackCapacity;
    private KnapsackProblem knapsackProblem;


    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField amountTF;

    @FXML
    private TextField backpackCapacityTF;

    public KnapsackController() {
        gridPane = new GridPane();
        hungarianController = new HungarianController();
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        hungarianController.backToMenu(event);
    }

    @FXML
    void dynamic() {
        initializeItemsList();
        knapsackProblem = new KnapsackProblem(items, backPackCapacity);
        knapsackProblem.fillMatrixP_Q();
        Knapsack knapsack = knapsackProblem.getDynamicKnapsack();
        displayResult(knapsack);

    }

    @FXML
    void enterData() {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        amountOfItems = Integer.parseInt(amountTF.getText());
        backPackCapacity = Integer.parseInt(backpackCapacityTF.getText());
        hungarianController.displayMatrix(amountOfItems, 2, borderPane, gridPane);
        List<String> list = RandomGenerator.generate(2 * amountOfItems);
        hungarianController.fillMatrix(list, gridPane);
    }

    @FXML
    void greedy() {

        initializeItemsList();
        knapsackProblem = new KnapsackProblem(items, backPackCapacity);
        Knapsack knapsack = knapsackProblem.greedy();
        displayResult(knapsack);
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

    private void displayOnScreen(List<String> data) {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        List<String> matrixNumbers = new ArrayList<>();
        amountTF.setText(data.get(0));
        backpackCapacityTF.setText(data.get(1));
        matrixNumbers.addAll(Arrays.asList(data.get(2).split(" ")));
        matrixNumbers.addAll(Arrays.asList(data.get(3).split(" ")));

        hungarianController.displayMatrix(matrixNumbers.size() / 2, 2, borderPane, gridPane);
        hungarianController.fillMatrix(matrixNumbers, gridPane);
    }

    @FXML
    void saveFile() {
        List<Integer> toSave = new ArrayList<>();
        toSave.add(amountOfItems);
        toSave.add(backPackCapacity);
        toSave.addAll(hungarianController.getNumbersFromScreen(gridPane));

        File file = hungarianController.getFileChooser().showSaveDialog(borderPane.getScene().getWindow());
        if (file != null) {
            FileSave.saveKnapsackProblem(file.getPath(), toSave);
        }
    }

    private void displayResult(Knapsack knapsack) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        Label totalValue = new Label("Value: " + knapsack.getTotalValue());
        vBox.getChildren().add(totalValue);
        knapsack.getItems().forEach((key, value) -> {
            Label label = new Label(key.getName() + " - " + value);
            vBox.getChildren().add(label);
        });
        borderPane.setCenter(vBox);
    }

    private void initializeItemsList() {
        List<Integer> dataFromScreen = hungarianController.getNumbersFromScreen(gridPane);
        items = new ArrayList<>();
        for (int i = 0; i < dataFromScreen.size() / 2; i++) {
            int price = dataFromScreen.get(i);
            int weight = dataFromScreen.get(i + amountOfItems);
            items.add(new Item(price, weight));
        }
    }


}
