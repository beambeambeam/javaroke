package javaroke.recommendation.controllers;

public class MainController {
    public static void main(String[] args) {
        RecommendSystem.printAdjacencyMetrix();;
        RecommendSystem.printPrevoiusVertexMetrix();
        System.out.println(RecommendSystem.getRecommendationItemList());
    }
}
