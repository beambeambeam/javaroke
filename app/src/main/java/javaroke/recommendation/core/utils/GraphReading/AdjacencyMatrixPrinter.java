package javaroke.recommendation.core.utils.GraphReading;

import java.util.List;

public class AdjacencyMatrixPrinter {
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

    public static void printPreviosVertexMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printPopularVertex(List<String> popularVertex) {
        System.out.print("Popular Vertex: ");
        for (String vertex : popularVertex) {
            System.out.print(vertex + " ");
        }
        System.out.println();
    }

    public static void printPath(List<String> path) {
        System.out.print(path.get(0) + "->" + path.get(path.size() - 1) + ": ");
        for (String node : path) {
            System.out.print(node + " ");
        }
        System.out.println();
    }
}
