package javaroke.models;

import java.util.ArrayList;
import java.util.List;
import javaroke.utils.Validator;

/**
 * This class likely represents a node in a graph data structure.
 */
public class NodeGraph {
  private String songId;
  private List<Edge> edges;
  private boolean isVisited;
  private int distance;

  public NodeGraph(String songId) {
    Validator.validateSongId(songId);
    this.songId = songId;
    this.edges = new ArrayList<>();
    this.isVisited = false;
    this.distance = Integer.MAX_VALUE;
  }

  /**
   * The getSongID() function in Java returns the song ID.
   *
   * @return The method `getSongID()` is returning the `songId` variable.
   */
  public String getSongID() {
    return songId;
  }

  /**
   * The function `getEdges()` returns a list of edges.
   *
   * @return A List of Edge objects is being returned.
   */
  public List<Edge> getEdges() {
    return edges;
  }

  /**
   * The function isVisited() returns the value of the isVisited boolean variable.
   *
   * @return The method `isVisited()` is returning the value of the `isVisited` variable, which is a
   *         boolean value indicating whether a certain condition has been visited or not.
   */
  public Boolean isVisited() {
    return isVisited;
  }

  /**
   * The `getDistance` function in Java returns the distance value.
   *
   * @return The method `getDistance()` is returning the value of the variable `distance`.
   */
  public int getDistance() {
    return distance;
  }

  /**
   * The setSongID function sets the song ID after validating it using a Validator class.
   *
   * @param songId The `setSongID` method is used to set the `songId` property of an object. The
   *        `songId` parameter is the unique identifier of a song that you want to set for the
   *        object. Before setting the `songId`, the method first validates the `songId` using
   */
  public void setSongID(String songId) {
    Validator.validateSongId(songId);
    this.songId = songId;
  }

  /**
   * The function sets the list of edges for a given object.
   *
   * @param edges The `setEdges` method you provided is a setter method for setting a list of `Edge`
   *        objects. The `edges` parameter in this method represents the list of edges that you want
   *        to set for the object.
   */
  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  /**
   * Sets the visited status of the node.
   *
   * @param isVisited a Boolean indicating whether the node has been visited (true) or not (false)
   */
  public void setVisited(Boolean isVisited) {
    this.isVisited = isVisited;
  }

  /**
   * Sets the distance for this node in the graph.
   *
   * @param distance the distance value to set
   */
  public void setDistance(int distance) {
    this.distance = distance;
  }

  /**
   * Increases the current distance by the specified value.
   *
   * @param distance the value to add to the current distance
   */
  public void plusDistance(int distance) {
    this.distance += distance;
  }

  /**
   * Adds an edge to the graph. If an edge with the same destination already exists, the weight of
   * the existing edge is incremented by the weight of the new edge. Otherwise, the new edge is
   * added to the list of edges.
   *
   * @param edge The edge to be added to the graph. It contains the destination node and the weight
   *        of the connection.
   */
  public void addEdge(Edge edge) {
    for (Edge e : edges) {
      if (e.getDestination().equals(edge.getDestination())) {
        e.plusWeight(edge.getWeight()); // Add weight to existing edge
        return; // Skip adding if edge exists
      }
    }

    // Edge doesn't exist, so add it
    this.edges.add(edge);
  }
}
