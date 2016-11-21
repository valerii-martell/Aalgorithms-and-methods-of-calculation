package com.gluonapplication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GluonApplicationContoller {
	//for lineal
    @FXML
    private TextField linealAField;
    @FXML
    private TextField linealResultField;
    //for branched
    @FXML
    private TextField branchedKField;
    @FXML
    private TextField branchedXField;
    @FXML
    private TextField branchedFField;
    @FXML
    private TextField branchedGField;
    @FXML
    private TextField branchedResultField;
    //for cyclic
    @FXML
    private TextField cyclicResultField;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */

    @FXML
    private void handleLinealCOMPUTE() {
    	if (isInputValidLineal()) {
        	double a=Double.parseDouble(linealAField.getText())/6;
        	double a1=Math.sin(a)+2*Math.sin(a);
        	linealResultField.setText(String.valueOf(a1));
    	}
    }
    @FXML
    private void handleBranchedCOMPUTE() {
        if (isInputValidBranched()) {
            double k = Double.parseDouble(branchedKField.getText());
            double x = Double.parseDouble(branchedXField.getText());
            double f = Double.parseDouble(branchedFField.getText());
            double g = Double.parseDouble(branchedGField.getText());
            double y = k*Math.sqrt(x)*Math.log10(f*g);
            branchedResultField.setText(String.valueOf(y));
        }
    }
    @FXML
    private void handleCyclicCOMPUTE() {
        	double y=0;
        	for (int a=1; a<=40;a+=4) {
        		y+=(Math.pow(a,4)+a);
            }
        	cyclicResultField.setText(String.valueOf(y));
    }
    
    @FXML
    private void handleLinealClean() {
        	linealAField.setText(""); 
        	linealResultField.setText("");
        	   
        }
    @FXML
    private void handleBranchedClean() {
    	 branchedKField.setText("");
    	 branchedXField.setText("");
    	 branchedFField.setText("");
    	 branchedGField.setText("");
    	 branchedResultField.setText("");
    }
    @FXML
    private void handleCyclicClean() {
    	 cyclicResultField.setText("");
    }


    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */

    private boolean isInputValidLineal() {
        String errorMessage = "";

        if (linealAField.getText() == null || linealAField.getText().length() == 0) {
            errorMessage += "No input value 'k'!\n"; 
        }else{
            try {
            	Double.parseDouble(linealAField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid value 'a' (must be an integer)!\n"; 
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        }else{
        	linealResultField.setText(errorMessage);
            return false;
    }
    }         
    
    private boolean isInputValidBranched() {
        String errorMessage = "";

        if (branchedKField.getText() == null || branchedKField.getText().length() == 0) {
            errorMessage += "No input value 'k'!\n"; 
        }else{
            try {
            	Double.parseDouble(branchedKField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid value 'k' (must be an integer)!\n"; 
            }
        }
        if (branchedXField.getText() == null || branchedXField.getText().length() == 0) {
            errorMessage += "No input value 'x'!\n"; 
        }else{
            try {
            	Double.parseDouble(branchedXField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid value 'x' (must be an integer)!\n"; 
            }
        }
        if (branchedFField.getText() == null || branchedFField.getText().length() == 0) {
            errorMessage += "No input value 'f'!\n"; 
        }else{
            try {
            	Double.parseDouble(branchedFField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid value 'f' (must be an integer)!\n"; 
            }
        }
        if (branchedGField.getText() == null || branchedGField.getText().length() == 0) {
            errorMessage += "No input value 'g'!\n"; 
        }else{
            try {
            	Double.parseDouble(branchedGField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid value 'g' (must be an integer)!\n"; 
            }
        }  
        if ((Double.parseDouble(branchedFField.getText())*Double.parseDouble(branchedGField.getText())) <= 0){
            errorMessage += "Expression of f*g must be> 0"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            branchedResultField.setText(errorMessage);
            return false;
        }
    }
}