package com.gluonapplication;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Error {
	private final SimpleIntegerProperty n;
    private final SimpleDoubleProperty estimationError;
    private final SimpleDoubleProperty exactError;
    private final SimpleDoubleProperty coefficientRefinement;
    
    public Error(int n, Double estimationError, Double exactError, Double coefficientRefinement) {
        this.n = new SimpleIntegerProperty(n);
        this.estimationError = new SimpleDoubleProperty(estimationError);
        this.exactError = new SimpleDoubleProperty(exactError);
        this.coefficientRefinement = new SimpleDoubleProperty(coefficientRefinement);   
    }
 
    public int getN() {
        return n.get();
    }
    public void setN(int n) {
        this.n.set(n);
    }
        
    public Double getEstimationError() {
        return estimationError.get();
    }
    public void setEstimationError(Double estimationError) {
        this.estimationError.set(estimationError);
    }
    
    public Double getExactError() {
        return exactError.get();
    }
    public void setExactError(Double exactError) {
        this.exactError.set(exactError);
    }
    
    public Double getCoefficientRefinement() {
        return coefficientRefinement.get();
    }
    public void setCoefficientRefinement(Double coefficientRefinement) {
        this.coefficientRefinement.set(coefficientRefinement);
    }
        
}