package com.gluonapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TableController {
	
    private Stage dialogStage;
    private Error error;
    
    @FXML
    TableView tableView;
    @FXML
    TableColumn nColumn;
    @FXML
    TableColumn estimationErrorColumn;
    @FXML
    TableColumn exactErrorColumn;
    @FXML
    TableColumn coefficientRefinementColumn;
    @FXML
    ObservableList<Error> errors = FXCollections.observableArrayList();

    public void setError(ObservableList<Error> errors) {
        this.errors = errors;
        nColumn.setCellValueFactory(
    			new PropertyValueFactory<Error, Integer>("n"));
        estimationErrorColumn.setCellValueFactory(
        		new PropertyValueFactory<Error, Double>("estimationError"));
        exactErrorColumn.setCellValueFactory(
        		new PropertyValueFactory<Error, Double>("exactError"));
        coefficientRefinementColumn.setCellValueFactory(
        		new PropertyValueFactory<Error, Double>("coefficientRefinement"));
       tableView.setItems(errors);
    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
