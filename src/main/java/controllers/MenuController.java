package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private FXMLLoader loader;

    public MenuController() {
        loader = new FXMLLoader();

    }

    @FXML
    void switchToHungarian(ActionEvent event) throws IOException {

        loader.setLocation(getClass().getResource("/hungarian.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(tableViewScene);
        currentStage.show();
    }

    @FXML
    void switchToKnapsackProblem(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/knapsack.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(tableViewScene);
        currentStage.show();
    }

    @FXML
    void switchToTSP(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/tsp.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(tableViewScene);
        currentStage.show();
    }

    @FXML
    void switchToJohnson(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/johnson.fxml"));
        Parent parent = loader.load();
        Scene tableViewScene = new Scene(parent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(tableViewScene);
        currentStage.show();
    }

}
