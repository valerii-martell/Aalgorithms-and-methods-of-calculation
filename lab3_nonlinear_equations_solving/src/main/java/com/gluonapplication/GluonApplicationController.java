package com.gluonapplication;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GluonApplicationController {
    ObservableList<String> functions = FXCollections.observableArrayList("x^2-sin(pi*x)=0", "Some another function");
    ObservableList<String> methods = FXCollections.observableArrayList("Method of chords", "Some another method");

    static XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    
    @FXML
    private ComboBox<String> functionsBox;
    @FXML
    private ComboBox<String> methodsBox;
    
    @FXML
    private TextField aField;
    @FXML
    private TextField bField;
    @FXML
    private TextField accuracyField;
    @FXML
    private TextField resultsField;   
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	functionsBox.setItems(functions);
    	methodsBox.setItems(methods);
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */

    @FXML
    private void handleCOMPUTE() {
    	
    	//if (functions.equals("x^2-sin(pi*x)=0")){
    		//if (methods.equals("Method of chords")){
    	double a = Double.parseDouble(aField.getText());
        double b = Double.parseDouble(bField.getText());
        double e = Double.parseDouble(accuracyField.getText());
        series.getData().clear();
        String result = new String();

        Solver solv = new Solver(a, b, e);
        LinkedList<Double> out = solv.getSolution();
        if (out.size() > 0) {
            Formatter fmt = new Formatter();
            for (int i = 0; i < out.size(); i++) {
                fmt.format("%10.5f", out.get(i));
                result = fmt + "; ";
                 // result = result + out.get(i) + "; ";
            }
        } else {
            result = "This interval not have any results";
        }

        double a1 = a - (b - a) * 0.2;
        double b1 = b + (b - a) * 0.2;
        int count = 1000;
        double step = (b1 - a1) / count;
        Function function = new Function();
        for (int i = 0; i <= (count); i++) {
            series.getData().add(new XYChart.Data<Number, Number>(a1, function.getValue(a1)));
            a1 += step;
        }
        resultsField.setText(result);
    	//}
    	//}
    	}
    
    @FXML
    private void handleCLEAN() {
    	aField.setText("");
    	bField.setText("");
    	accuracyField.setText("");
        resultsField.setText("");
    }
    
    @SuppressWarnings("unchecked")
	@FXML
	public void showFunctionChart() {
    	 Stage stage = new Stage();
    	 stage.setTitle("Function Chart");
    	 stage.getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
    	 
    	 AnchorPane pane = new AnchorPane();
    	 pane.setPrefSize(360, 600);
    	 pane.setMinSize(360, 600);
    	 pane.setMaxSize(360, 600);
    	 pane.setStyle("-fx-background-color: #ffffff");	 
    	 
    	 VBox vbox = new VBox();
    	 vbox.setPrefSize(600, 360);
    	 vbox.setMinSize(600, 360);
    	 vbox.setMaxSize(600, 360);
    	 HBox hbox1 = new HBox();
    	 Label title = new Label("Function Chart");
         title.getStyleClass().add("card-title");
         title.setStyle("-fx-text-fill:  #2196F3");
    	 hbox1.getChildren().add(title);
    	 hbox1.setAlignment(Pos.CENTER);
    	 pane.getStylesheets().add("material-fx-v0_3.css");
         final NumberAxis xAxis = new NumberAxis();
         final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("X");
         yAxis.setLabel("Y");
         final LineChart<Number,Number> lineChart=new LineChart<Number,Number>(xAxis,yAxis);
         lineChart.getData().add(series);
         //if (functions.equals("x^2-sin(pi*x)=0")){
         series.setName("x^2-sin(pi*x)");
         //}
         HBox hbox2 = new HBox();
         Button close = new Button("CLOSE");
         close.setOnAction(event -> {
        	 stage.close();
         });
         close.getStyleClass().add("button-raised");
         hbox2.getChildren().add(close);
         hbox2.setAlignment(Pos.CENTER_RIGHT);
         vbox.getChildren().addAll(hbox1,lineChart,hbox2);
         vbox.getStyleClass().add("vertical");
         vbox.setTranslateX(-122);
         vbox.setTranslateY(125);
         
         pane.getChildren().add(vbox);

         Scene scene  = new Scene(pane,360,600);
         stage.setScene(scene);
         stage.show();        
    }  
}