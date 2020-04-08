package controllers;

import algorithms.HungarianMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import utils.FileParser;
import utils.FileSave;
import utils.Matrix;
import utils.RandomGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HungarianController {

    @Getter
    private FileChooser fileChooser;
    private Label enterMatrixSizeLabel;
    private TextField matrixSize;
    private Button enterSizeButton;

    @Getter
    private GridPane gridPane;
    @FXML
    private Button hungarianMethod;
    private Label sum;
    private Matrix matrix;


    @FXML
    private BorderPane mainBorderPane;

    public HungarianController() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setSelectedExtensionFilter(extFilter);
        enterMatrixSizeLabel = new Label();
        enterMatrixSizeLabel.setText("Enter matrix size: ");
        matrixSize = new TextField();
        enterSizeButton = new Button("Enter size");
        gridPane = new GridPane();
        sum = new Label();


    }

    @FXML
    void initialize() {
        enterSizeButton.setOnAction(event -> {
            gridPane.getChildren().remove(0, gridPane.getChildren().size());
            int matrSize = Integer.parseInt(matrixSize.getText());
            displayMatrix(matrSize, matrSize, mainBorderPane, gridPane);
            fillMatrix(RandomGenerator.generate(gridPane.getChildren().size()), gridPane);

            hungarianMethod.setDisable(false);
        });
        hungarianMethod.setOnAction(event -> {
            setCellsWhite();
            initializeMatrix(getNumbersFromScreen(gridPane));

            HungarianMethod hungarianMethod = new HungarianMethod(matrix);
            Matrix solvedMatrix = hungarianMethod.solve();
            lightCells(solvedMatrix);
            sum.setText("Sum: " + HungarianMethod.sum(matrix, solvedMatrix));
        });
    }

    private void lightCells(Matrix solvedMatrix) {
        int[] indexesToLight = Stream.of(solvedMatrix.getMatrixElements()).flatMapToInt(IntStream::of).toArray();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            if (indexesToLight[i] == 1) {
                gridPane.getChildren().get(i).setStyle("-fx-control-inner-background:#FFFF00;");
            }
        }
    }

    private void setCellsWhite() {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            gridPane.getChildren().get(i).setStyle("-fx-control-inner-background:#F8F8FF;");

        }
    }


    @FXML
    void enterFromKeyboard() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        matrixSize.setText("5");

        hBox.getChildren().addAll(enterMatrixSizeLabel, matrixSize, enterSizeButton);
        hBox.setAlignment(Pos.CENTER);
        BorderPane.setMargin(hBox, new Insets(0, 0, 20, 0));
        mainBorderPane.setBottom(hBox);

    }

    @FXML
    void openFile() {
        File file = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

        if (file != null) {
            matrix = FileParser.parseForHungarianMethod(file.getPath());
        }

        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        int matrixSize = matrix.getSize();
        displayMatrix(matrixSize, matrixSize, mainBorderPane, gridPane);

        List<String> matrixElementsAsString = new ArrayList<>();

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {

                matrixElementsAsString.add(String.valueOf(matrix.getMatrixElements()[i][j]));
            }
        }

        fillMatrix(matrixElementsAsString, gridPane);

        hungarianMethod.setDisable(false);
    }

    @FXML
    void saveFile() {

        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());
        if (file != null) {
            FileSave.saveHungarianMethod(file.getPath(), getNumbersFromScreen(gridPane));
        }
    }

    public List<Integer> getNumbersFromScreen(GridPane gridPane) {
        List<TextField> textFields = new ArrayList<>();
        gridPane.getChildren().forEach(node -> textFields.add((TextField) node));

        List<Integer> numbers = new ArrayList<>();
        textFields.forEach(textField -> numbers.add(Integer.valueOf(textField.getText())));

        return numbers;
    }

    private void initializeMatrix(List<Integer> numbers) {
        matrix = new Matrix((int) Math.sqrt(numbers.size()));
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                matrix.getMatrixElements()[j][i] = numbers.get(j * matrix.getSize() + i);
            }
        }


    }

    public void displayMatrix(int width, int height, BorderPane borderPane, GridPane gridPane) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                TextField tf = createMatrixCell();

                GridPane.setRowIndex(tf, y);
                GridPane.setColumnIndex(tf, x);
                gridPane.getChildren().add(tf);
                gridPane.setAlignment(Pos.CENTER);
            }
        }
        VBox vBox = new VBox();
        vBox.setSpacing(0);
        vBox.getChildren().addAll(gridPane, sum);
        vBox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(vBox);
        borderPane.setCenter(scrollPane);
    }

    public void fillMatrix(List<String> matrixCells, GridPane gridPane) {
        int matrixSize = gridPane.getChildren().size();
        for (int i = 0; i < matrixSize; i++) {
            TextField textField = (TextField) gridPane.getChildren().get(i);
            textField.setText(matrixCells.get(i));
        }
    }

    private TextField createMatrixCell() {
        TextField tf = new TextField();
        tf.setPrefHeight(35);
        tf.setPrefWidth(35);
        tf.setAlignment(Pos.CENTER);
        tf.setEditable(true);
        return tf;
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/menu.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(tableViewScene);
        currentStage.show();
    }


}