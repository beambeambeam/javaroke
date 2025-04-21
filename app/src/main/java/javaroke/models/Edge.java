package javaroke.models;

import javaroke.utils.Validator;

/**
 * This class likely represents an edge in a graph data structure.
 */
public class Edge {
  private String destination;
  private int weight;

  public Edge(String destination, int weight) {
    this.destination = destination;
    this.weight = weight;
  }

  /**
   * The getDestination function in Java returns the destination value.
   *
   * @return The `destination` variable is being returned.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * The getWeight() function returns the weight value.
   *
   * @return The method `getWeight()` is returning the value of the variable `weight`.
   */
  public int getWeight() {
    return weight;
  }

  /**
   * The setDestination method in Java sets the destination of a song after validating the song ID.
   *
   * @param destination The `setDestination` method is used to set the destination of a song. The
   *        `destination` parameter represents the identifier of the destination where the song will
   *        be set. Before setting the destination, the method validates the `destination` using the
   *        `validateSongId` method from the `Validator` class
   */
  public void setDestination(String destination) {
    Validator.validateSongId(destination);
    this.destination = destination;
  }

  /**
   * The function setWeight(int weight) sets the weight of an object to the specified value.
   *
   * @param weight The `setWeight` method is a setter method used to assign a value to the `weight`
   *        instance variable of an object. The `weight` parameter in this method represents the new
   *        value that will be assigned to the `weight` instance variable.
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }

  /**
   * The `plusWeight` function in Java adds a specified weight to the current weight of an object.
   *
   * @param weight The `plusWeight` method takes an integer parameter `weight` as input. This method
   *        adds the value of the `weight` parameter to the current weight of the object
   *        (`this.weight`).
   */
  public void plusWeight(int weight) {
    this.weight += weight;
  }

}
