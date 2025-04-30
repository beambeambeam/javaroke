package javaroke.reccomendation.core.utils;

public class AdjacencyMatrixUtils {
    public static void printAdjacencyMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.3f", matrix[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
