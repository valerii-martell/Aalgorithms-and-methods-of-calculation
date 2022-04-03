package com.gluonapplication;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    ObservableList<String> typeInterpolation = FXCollections.observableArrayList("Lagrange", "Newton", "Aitken");
    ObservableList<String> typeFunction = FXCollections.observableArrayList("sin(x)", "sin(x^2)*exp(-(x^2)/4)");
    ObservableList<Error> errors = FXCollections.observableArrayList();

    static XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
    static XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
    static XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();
    
    @FXML
    private ComboBox<String> typeInterpolationBox;
    @FXML
    private ComboBox<String> typeFunctionBox;
    
    @FXML
    private TextField aField;
    @FXML
    private TextField bField;
    @FXML
    private TextField countField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML 
    private TextField errorField;
    @FXML
    private TextField errorErrorField;
    @FXML
    private TextField rdeField;
 
    private Tooltip aTooltip = new Tooltip("Left border");
    private Tooltip bTooltip = new Tooltip("Right border");
    private Tooltip countTooltip = new Tooltip("Points count");
    private Tooltip xTooltip = new Tooltip("X");
    private Tooltip yTooltip = new Tooltip("Y");
    private Tooltip errorTooltip = new Tooltip("Error");
    private Tooltip errorErrorTooltip = new Tooltip("Error error");
    private Tooltip rdeTooltip = new Tooltip("Relative dimness error");
    
   
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	typeInterpolationBox.setItems(typeInterpolation);;
        typeFunctionBox.setItems(typeFunction);
        aField.setTooltip(aTooltip);
        bField.setTooltip(bTooltip);;
        countField.setTooltip(countTooltip);
        xField.setTooltip(xTooltip);
        yField.setTooltip(yTooltip);
        errorField.setTooltip(errorTooltip);
        errorErrorField.setTooltip(errorErrorTooltip);
        rdeField.setTooltip(rdeTooltip);
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */

    @FXML
    private void handleCOMPUTE() {
    	series1.getData().clear();
    	series2.getData().clear();
    	series3.getData().clear();
    	double a = Double.parseDouble(aField.getText());
        double b = Double.parseDouble(bField.getText());
        double xx = Double.parseDouble(xField.getText());
        int count = Integer.parseInt(countField.getText());
        double step = (b - a) / count;
        
        String typeInt = (String) typeInterpolationBox.getValue();
        String typeFun = (String) typeFunctionBox.getValue();
        
        Interpolation src = new Interpolation(a, b, count);
        src.setY(typeFun);

        int c1 = 100;
        double step1 = step / c1;
        
        if (typeFun.equals("sin(x)")) {
            for (int i = 0; i <= (count * c1); i++) {
                series1.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, Math.sin(a + step1 * i)));
                series2.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, src.interp(typeInt, a + step1 * i)));
                series3.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, src.getError(typeFun, typeInt, a + step1 * i)));
            }
        }

        if (typeFun.equals("sin(x^2)*exp(-(x^2)/4)")) {
            for (int i = 0; i <= (count * c1); i++) {
            	 series1.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, Math.sin(Math.pow((a + step1 * i), 2)) * Math.exp(-(a + step1 * i) * (a + step1 * i) / 4.0)));
                 series2.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, src.interp(typeInt, a + step1 * i)));
                 series3.getData().add(new XYChart.Data<Number, Number>(a + step1 * i, src.getError(typeFun, typeInt, a + step1 * i)));
            }
        }
        double r1=src.interp(typeInt, xx);
        double r2=src.getError(typeFun, typeInt, xx);
        double r3=src.getErrorOfError(typeFun, typeInt, xx);
       	double r4=src.getBluriness(typeFun, typeInt, xx);
       	
       	r1 = new BigDecimal(r1).setScale(3, RoundingMode.UP).doubleValue();
       	r2 = new BigDecimal(r2).setScale(3, RoundingMode.UP).doubleValue();
       	r3 = new BigDecimal(r3).setScale(3, RoundingMode.UP).doubleValue();
       	r4 = new BigDecimal(r4).setScale(3, RoundingMode.UP).doubleValue();
       	
        yField.setText(String.valueOf(r1));
        errorField.setText(String.valueOf(r2));
        errorErrorField.setText(String.valueOf(r3));
        rdeField.setText(String.valueOf(r4));
 

        for (int i = 1; i <= count; i++) {
            Interpolation src1 = new Interpolation(a, b, i);
            src1.setY(typeFun);
            double p1 = src1.getError(typeFun, typeInt, xx);
            double p2 = 0;
            if (typeFun.equals("sin(x)"))
                p2 = src1.interp(typeInt, xx) - Math.sin(xx);
            if (typeFun.equals("sin(x^2)*exp(-(x^2)/4)"))
                p2 = src1.interp(typeInt, xx) - Math.sin(Math.pow(xx, 2)) * Math.exp(-xx * xx / 4.0);
            double p3 = 1 - p2 / p1;
            p1 = new BigDecimal(p1).setScale(3, RoundingMode.UP).doubleValue();
            p2 = new BigDecimal(p2).setScale(3, RoundingMode.UP).doubleValue();
            p3 = new BigDecimal(p3).setScale(3, RoundingMode.UP).doubleValue();
            errors.add(new Error(i,p1,p2,p3));
        }
    }


    	/*if (isInputValidLineal()) {
        	double a=Double.parseDouble(linealAField.getText())/6;
        	double a1=Math.sin(a)+2*Math.sin(a);
        	linealResultField.setText(String.valueOf(a1));
    	}*/
    
    
    
    @FXML
    private void handleCLEAN() {
    	aField.setText("");
    	bField.setText("");
    	countField.setText("");
    	xField.setText("");
    	yField.setText("");
    	errorField.setText("");
    	errorErrorField.setText("");
    	rdeField.setText("");
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
         lineChart.getData().addAll(series1, series2);
         series1.setName("Issuing function");
         series2.setName("Interpolation function");
    	 
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
    
    @FXML
    public void showErrorChart() {
    	Stage stage = new Stage();
   	 	stage.setTitle("Error Chart");
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
   	 	Label title = new Label("Error Chart");
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
        lineChart.getData().add(series3);
        series3.setName("Error");
   	 
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
    
    @FXML
	public void showTable() {
		try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GluonApplication.class.getResource("/table.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Errors table");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            dialogStage.getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
            
            TableController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setError(errors);
            
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}