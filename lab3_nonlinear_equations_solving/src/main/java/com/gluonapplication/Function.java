package com.gluonapplication;

public class Function {
	
	//функція
    public double getValue(double x){
        return x*x - Math.sin(Math.PI*x);
    }
    
    //перша похідна
    public double getFirstDerivative(double x){
        return 2*x - Math.PI*Math.cos(Math.PI*x);
    }
    
    //друга похідна
    public double getSecondDerivative(double x){
        return 2 + Math.PI*Math.PI*Math.sin(Math.PI * x);
    }
}
