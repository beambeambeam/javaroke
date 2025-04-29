package javaroke.reccomendation.models;

import java.util.ArrayList;

public class MatrixWithLabels {
    ArrayList<ArrayList<Double>> matrix;
    ArrayList<String> labels;
    int size;


    public MatrixWithLabels(ArrayList<ArrayList<Double>> matrix, ArrayList<String> labels,
            int size) {
        this.matrix = matrix;
        this.labels = labels;
        this.size = size;
    }
}
