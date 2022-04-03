package com.gluonapplication;

public class Solver {
    private int dimension;
    private double[][] factorArray;
    private double[] absoluteArray;
    private double[] solutionArray;
    private boolean unicityOfSystem;
    private boolean solvabilityOfSystem;

    public Solver(int dimension, double[][] factorArray, double[] absoluteArray) {
        setDimension(dimension);
        setFactorArray(factorArray);
        setAbsoluteArray(absoluteArray);
        unicityOfSystem = true;
        solvabilityOfSystem = true;
        solutionArray = new double[dimension];
    }

    public void solve() {

        for (int i = 0; i < dimension; i++) {

            //Перевірка рядку на 0
            boolean flag = false;
            for (int j = i; j < dimension; j++) {
                if (factorArray[i][j] != 0) {
                    flag = true;
                }
            }
            if (flag == false) {
                if (absoluteArray[i] == 0) {
                    unicityOfSystem = false;
                } else {
                    solvabilityOfSystem = false;
                }
                break;
            }

            //Пересув рядків
            double temp = factorArray[i][i];
            int tempN = i;
            for (int j = i + 1; j < dimension; j++) {
                if (Math.abs(factorArray[j][i]) > Math.abs(temp)) {
                    temp = factorArray[j][i];
                    tempN = j;
                }
            }
            if (tempN != i) {
                for (int j = i; j < dimension; j++) {
                    double buf = factorArray[i][j];
                    factorArray[i][j] = factorArray[tempN][j];
                    factorArray[tempN][j] = buf;
                }

                double buf = absoluteArray[i];
                absoluteArray[i] = absoluteArray[tempN];
                absoluteArray[tempN] = buf;
            }

            //Коефіцієнти і-го рядка
            for (int j = i + 1; j < dimension; j++) {
                factorArray[i][j] = factorArray[i][j] / factorArray[i][i];
            }
            absoluteArray[i] = absoluteArray[i] / factorArray[i][i];
            factorArray[i][i] = 1;

            //Віднімання коефіцієнтів
            for (int j = 0; j < dimension; j++) {
                if (j != i) {
                    for (int k = i + 1; k < dimension; k++) {
                        factorArray[j][k] = factorArray[j][k] - factorArray[i][k] * factorArray[j][i];
                    }
                    absoluteArray[j] = absoluteArray[j] - absoluteArray[i] * factorArray[j][i];
                }
            }
        }

        for (int i = 0; i < dimension; i++) {
            solutionArray[i] = absoluteArray[i];
        }
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
       this.dimension = dimension;
    }

    public double[][] getFactorArray() {
        return factorArray;
    }

    public void setFactorArray(double[][] factorArray) {
        this.factorArray = factorArray;
    }

    public double[] getAbsoluteArray() {
        return absoluteArray;
    }

    public void setAbsoluteArray(double[] absoluteArray) {
        this.absoluteArray = absoluteArray;
    }

    public double[] getSolutionArray() {
        return solutionArray;
    }

    public boolean isUnicityOfSystem() {
        return unicityOfSystem;
    }

    public boolean isSolvabilityOfSystem() {
        return solvabilityOfSystem;
    }
}
