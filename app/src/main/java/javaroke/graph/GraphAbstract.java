package javaroke.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javaroke.models.NodeGraph;

/**
 * The `GraphAbstract` class provides a framework for creating and manipulating graph data
 * structures with methods to add nodes, show adjacency matrix, and add edges.
 */
public abstract class GraphAbstract {
  /*
   * numVertices: indicate the size of both adjacencyList and idList adjacencyList: be HashMap, use
   * to keep map of all NodeGraph with idSong idList: be TreeSet, use to keep all idSong that sort
   * by character
   *
   * In normal use, we will only use adjacencyList to find all node But when we want to read
   * adjacency Metrix, we have to use idList to make correct metrix cause hashMap order may not the
   * same between adjacencyList order and in self Node order
   */
  protected int numVertices;
  protected Map<String, NodeGraph> adjacencyList;
  protected Set<String> idList;

  public GraphAbstract() {
    this.numVertices = 0;
    this.adjacencyList = new HashMap<>();
    this.idList = new TreeSet<>();
  }

  /**
   * The `addNode` function adds a new node to a graph data structure if it does not already exist.
   *
   * @param name The `name` parameter in the `addNode` method represents the name of the node that
   *        you want to add to the graph data structure.
   */
  protected void addNode(String name) {
    // HashMap containsKey O(1)
    // But TreeSet is O(log n)
    if (!adjacencyList.containsKey(name)) {
      idList.add(name);
      adjacencyList.put(name, new NodeGraph(name));
      numVertices++;
    }
  }

  /**
   * The function `showAdjacencyMatrix` prints out an adjacency matrix representation of a graph
   * along with the list of nodes and their edges.
   */
  public void showAdjacencyMatric() {
    idList.forEach((idSongY) -> {
      idList.forEach((idSongX) -> {
        System.out.print(adjacencyList.get(idSongY).getEdges().stream()
            .anyMatch(edge -> edge.getDestination().equals(idSongX)) ? "1 " : "0 ");
      });
      System.out.println();
    });
    adjacencyList.forEach((key, node) -> {
      System.out.println("Node: " + key + ", Edges: " + node.getEdges());
    });
  }

  /**
   * This abstract method is used to add an edge between two vertices with a specified weight in a
   * graph data structure.
   *
   * @param source The source parameter represents the starting point of the edge in a graph.
   * @param destination The `destination` parameter in the `addEdge` method represents the target
   *        node to which the edge is being added. This parameter specifies the node that the edge
   *        will connect to from the source node.
   * @param weight The `weight` parameter in the `addEdge` method represents the weight or cost
   *        associated with the edge between the source and destination vertices in a graph. This
   *        weight could represent various things depending on the context of the graph, such as
   *        distance, cost, time, or any other relevant metric.
   */
  public abstract void addEdge(String source, String destination, int weight);
}
