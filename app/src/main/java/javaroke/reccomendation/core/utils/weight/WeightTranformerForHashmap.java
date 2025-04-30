package javaroke.reccomendation.core.utils.weight;

import javaroke.reccomendation.core.graphs.GraphHashMap;

public class WeightTranformerForHashmap {
    // The default graph used positive weights for popularity
    // The more weight it has mean the more popular it is
    // But for shortest path algorithm we need to use flip this weights
    // Flip to the less weight it has mean the more pupular it is

    // floorWeight = maximumWeight + 1 (The less popular it is)
    public static void flipWeight(GraphHashMap graph) {
        for (String src : graph.allKeyList) {
            for (String dest : graph.allKeyList) {
                if (!graph.adjacencyList.containsKey(src)
                        || !graph.adjacencyList.get(src).containsKey(dest)) {
                    continue;
                }

                // Maxvalue - M[src][dest] + floorValue + 1.0
                double newWeight = graph.maximumWeight - graph.adjacencyList.get(src).get(dest) + 1;

                graph.adjacencyList.get(src).put(dest, newWeight);
            }
        }
        graph.floorWeight = graph.maximumWeight + 1;
    }

    // floorWeight = maximumWeight + bias + 1 (The less popular it is)
    // But have bias ranges between floor and lowest value
    public static void addBias2Floor(GraphHashMap graph, double bias) {
        graph.floorWeight += bias;
    }

    public static void additive2Weight(GraphHashMap graph, double value) {
        for (String src : graph.allKeyList) {
            for (String dest : graph.allKeyList) {
                if (!graph.adjacencyList.containsKey(src)
                        || !graph.adjacencyList.get(src).containsKey(dest)) {
                    continue;
                }

                // M[src][dest] + value
                double newWeight = graph.adjacencyList.get(src).get(dest) + value;

                graph.adjacencyList.get(src).put(dest, newWeight);
            }
        }
    }

    public static void exponential2Weight(GraphHashMap graph, double power) {
        for (String src : graph.allKeyList) {
            for (String dest : graph.allKeyList) {
                if (!graph.adjacencyList.containsKey(src)
                        || !graph.adjacencyList.get(src).containsKey(dest)) {
                    continue;
                }

                // M[src][dest] ^ power
                double newWeight = Math.pow(graph.adjacencyList.get(src).get(dest), power);

                graph.adjacencyList.get(src).put(dest, newWeight);
            }
        }
        graph.floorWeight = Math.pow(graph.floorWeight, power);
    }

    public static void normalize0to1(GraphHashMap graph) {
        for (String src : graph.allKeyList) {
            for (String dest : graph.allKeyList) {
                if (!graph.adjacencyList.containsKey(src)
                        || !graph.adjacencyList.get(src).containsKey(dest)) {
                    continue;
                }

                // M[src][dest] / floorWeight
                double newWeight = graph.adjacencyList.get(src).get(dest) / graph.floorWeight;

                graph.adjacencyList.get(src).put(dest, newWeight);
            }
        }
        graph.floorWeight = 1.0;
    }

    public static void normalizeScaleWeight(GraphHashMap graph, double scale) {
        for (String src : graph.allKeyList) {
            for (String dest : graph.allKeyList) {
                if (!graph.adjacencyList.containsKey(src)
                        || !graph.adjacencyList.get(src).containsKey(dest)) {
                    continue;
                }

                // M[src][dest] / scale
                double newWeight = graph.adjacencyList.get(src).get(dest) * scale;

                graph.adjacencyList.get(src).put(dest, newWeight);
            }
        }
        graph.floorWeight = 1.0;
    }
}
