package controllers;

import algorithms.TSP;
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
import utils.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPController {
    private GridPane gridPane;
    private HungarianController hungarianController;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField amountTF;

    @FXML
    private TextField XleftPointTF;

    @FXML
    private TextField YleftPointTF;

    @FXML
    private TextField XrightPointTF;

    @FXML
    private TextField YrightPointTF;

    public TSPController() {
        hungarianController = new HungarianController();
        gridPane = hungarianController.getGridPane();

    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        hungarianController.backToMenu(event);
    }

    @FXML
    void enterData() {
        int pointsAmount = Integer.parseInt(amountTF.getText());
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        hungarianController.displayMatrix(2, pointsAmount, borderPane, gridPane);

        Point leftPoint = Point.of(Integer.parseInt(XleftPointTF.getText()), Integer.parseInt(YleftPointTF.getText()));
        Point rightPoint = Point.of(Integer.parseInt(XrightPointTF.getText()), Integer.parseInt(YrightPointTF.getText()));
        List<String> points = Point.getPointsAsStrings(pointsAmount, leftPoint, rightPoint);
        hungarianController.fillMatrix(points, gridPane);
    }

    @FXML
    void openFile() {
        File file = hungarianController.getFileChooser().showOpenDialog(borderPane.getScene().getWindow());
        List<String> data = null;
        if (file != null) {
            data = FileParser.parseForTSP(file.getPath());
        }

        displayOnScreen(data);

    }

    @FXML
    void saveFile() {
        List<Integer> toSave = new ArrayList<>(hungarianController.getNumbersFromScreen(gridPane));

        File file = hungarianController.getFileChooser().showSaveDialog(borderPane.getScene().getWindow());
        if (file != null) {
            FileSave.saveTSP(file.getPath(), toSave);
        }
    }

    @FXML
    void solveHeuristic() {
        TSP tsp = new TSP(getPointsFromScreen());
        displaySolve(tsp.solve(0));
    }

    private void displaySolve(List<Integer> solve) {
        VBox vBox = new VBox();
        String cost = String.valueOf(solve.get(solve.size() - 1));
        StringBuilder path = new StringBuilder();
        Label costLabel = new Label("Cost: " + cost);

        for (int i = 0; i < solve.size() - 1; i++) {
            String vertex = String.valueOf(solve.get(i));
            path.append(vertex);
            path.append("-");
        }
        path.deleteCharAt(path.length() - 1);

        Label pathLabel = new Label("Path: " + path.toString());

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(costLabel, pathLabel);

        borderPane.setCenter(vBox);
    }

    private List<Point> getPointsFromScreen() {
        List<Integer> numbersFromScreen = hungarianController.getNumbersFromScreen(gridPane);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < numbersFromScreen.size() - 1; i += 2) {
            Point point = Point.of(numbersFromScreen.get(i), numbersFromScreen.get(i + 1));
            points.add(point);
        }
        return points;
    }

    public void displayOnScreen(List<String> toDisplay) {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        List<String> data = new ArrayList<>();
        toDisplay.stream().forEach(s -> {
            data.add(s.split(" ")[0]);
            data.add(s.split(" ")[1]);
        });

        hungarianController.displayMatrix(2, data.size() / 2, borderPane, gridPane);
        hungarianController.fillMatrix(data, gridPane);
    }
}