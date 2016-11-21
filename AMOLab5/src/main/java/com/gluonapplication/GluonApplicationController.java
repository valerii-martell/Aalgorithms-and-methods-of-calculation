package com.gluonapplication;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GluonApplicationController {
 
    @FXML
    private TextField x01;
    @FXML
    private TextField x02;
    @FXML
    private TextField x03;
    @FXML
    private TextField x04;
    @FXML
    private TextField x11;
    @FXML
    private TextField x12;
    @FXML
    private TextField x13;
    @FXML
    private TextField x14;
    @FXML
    private TextField x21;
    @FXML
    private TextField x22;
    @FXML
    private TextField x23;
    @FXML
    private TextField x24;
    @FXML
    private TextField x31;
    @FXML
    private TextField x32;
    @FXML
    private TextField x33;
    @FXML
    private TextField x34;
    @FXML
    private TextField y1;
    @FXML
    private TextField y2;
    @FXML
    private TextField y3;
    @FXML
    private TextField y0;
    @FXML
    private TextField x1;
    @FXML
    private TextField x2;
    @FXML
    private TextField x3;
    @FXML
    private TextField x4;
    @FXML
    private Label mLabel;
   
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	/*myNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
    	            if(mouseEvent.getClickCount() == 2){
    	                System.out.println("Double clicked");
    	            }
    	        }
    	    }
    	});*/
    }

    @FXML
    private void handleCOMPUTE() {
    	double[][] factoryArray = new double[4][4];
        double[] absoluteArray = new double[4];

        factoryArray[0][0] = Double.parseDouble(x01.getText());
        factoryArray[0][1] = Double.parseDouble(x02.getText());
        factoryArray[0][2] = Double.parseDouble(x03.getText());
        factoryArray[0][3] = Double.parseDouble(x04.getText());

        factoryArray[1][0] = Double.parseDouble(x11.getText());
        factoryArray[1][1] = Double.parseDouble(x12.getText());
        factoryArray[1][2] = Double.parseDouble(x13.getText());
        factoryArray[1][3] = Double.parseDouble(x14.getText());

        factoryArray[2][0] = Double.parseDouble(x21.getText());
        factoryArray[2][1] = Double.parseDouble(x22.getText());
        factoryArray[2][2] = Double.parseDouble(x23.getText());
        factoryArray[2][3] = Double.parseDouble(x24.getText());

        factoryArray[3][0] = Double.parseDouble(x31.getText());
        factoryArray[3][1] = Double.parseDouble(x32.getText());
        factoryArray[3][2] = Double.parseDouble(x33.getText());
        factoryArray[3][3] = Double.parseDouble(x34.getText());

        absoluteArray[0] = Double.parseDouble(y0.getText());
        absoluteArray[1] = Double.parseDouble(y1.getText());
        absoluteArray[2] = Double.parseDouble(y2.getText());
        absoluteArray[3] = Double.parseDouble(y3.getText());

        Solver solver = new Solver(4, factoryArray, absoluteArray);
        
        solver.solve();
        if (solver.isSolvabilityOfSystem() == false) {
        	mLabel.setVisible(true);
        	mLabel.setText("No solutions");
        } else {
            if (solver.isUnicityOfSystem() == false) {
            	mLabel.setVisible(true);
            	mLabel.setText("Many solutions");
            } else {
            	mLabel.setVisible(false);
            	
                double[] solution = solver.getSolutionArray();
                
                double r0=solution[0];
                double r1=solution[1];
                double r2=solution[2];
                double r3=solution[3];
                
               	r0 = new BigDecimal(r0).setScale(3, RoundingMode.UP).doubleValue();
               	r1 = new BigDecimal(r1).setScale(3, RoundingMode.UP).doubleValue();
               	r2 = new BigDecimal(r2).setScale(3, RoundingMode.UP).doubleValue();
               	r3 = new BigDecimal(r3).setScale(3, RoundingMode.UP).doubleValue();
               	
                x1.setText(String.valueOf(r0));
                x2.setText(String.valueOf(r1));
                x3.setText(String.valueOf(r2));
                x4.setText(String.valueOf(r3));
            }
        }
    }
    
    @FXML
    private void handleCLEAN() {
    	mLabel.setVisible(false);
    	x01.clear();
    	x02.clear();
    	x03.clear();
    	x04.clear();
    	x11.clear();
    	x12.clear();
    	x13.clear();
    	x14.clear();
    	x21.clear();
    	x22.clear();
    	x23.clear();
    	x24.clear();
    	x31.clear();
    	x32.clear();
    	x33.clear();
    	x34.clear();
    	y0.clear();
    	y1.clear();
    	y2.clear();
    	y3.clear();
    	x1.clear();
    	x2.clear();
    	x3.clear();
    	x4.clear();
    }
    
    
    /**
     * Closes the application.
     */
    @FXML
    private void handleCLOSE() {
        System.exit(0);
    }  
}