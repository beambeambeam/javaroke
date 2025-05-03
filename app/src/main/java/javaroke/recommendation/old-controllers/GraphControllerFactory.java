package javaroke.recommendation.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javaroke.recommendation.controllers.implement.GraphHashMapController;

/**
 * Factory for creating various types of graph controllers. This class follows the Factory pattern
 * to create and manage different controller implementations.
 */
public class GraphControllerFactory {
    private static final Logger LOGGER = Logger.getLogger(GraphControllerFactory.class.getName());

    // Singleton instance
    private static GraphControllerFactory instance;

    // Registry of controller types
    private final Map<String, ControllerCreator<?>> controllerRegistry;

    // Private constructor for singleton pattern
    private GraphControllerFactory() {
        controllerRegistry = new HashMap<>();

        // Register default controller types
        registerControllerType("hashmap",
                (savePath, version, loadPath) -> loadPath == null
                        ? new GraphHashMapController(savePath, version)
                        : new GraphHashMapController(savePath, version, loadPath));

        // Add additional controller types here
        // registerControllerType("2dtree", (savePath, version, loadPath) ->
        // loadPath == null ?
        // new Coordinate2DTreeController(savePath, version) :
        // new Coordinate2DTreeController(savePath, version, loadPath)
        // );
    }

    /**
     * Get the singleton instance of the factory
     */
    public static synchronized GraphControllerFactory getInstance() {
        if (instance == null) {
            instance = new GraphControllerFactory();
        }
        return instance;
    }

    /**
     * Register a new controller type with the factory
     * 
     * @param type The identifier for this controller type
     * @param creator The creator function that creates controllers of this type
     */
    public <T extends RecommendationController<?, ?>> void registerControllerType(String type,
            ControllerCreator<T> creator) {
        controllerRegistry.put(type.toLowerCase(), creator);
        LOGGER.info("Registered controller type: " + type);
    }

    /**
     * Create a controller of the specified type with a new graph
     * 
     * @param type The type of controller to create
     * @param savePath The path to save the graph
     * @param version The version name to use
     * @return A new controller instance
     */
    public RecommendationController<?, ?> createController(String type, String savePath,
            String version) {
        return createController(type, savePath, version, null);
    }

    /**
     * Create a controller of the specified type with an existing graph
     * 
     * @param type The type of controller to create
     * @param savePath The path to save the graph
     * @param version The version name to use
     * @param loadPath The path to load the graph from
     * @return A new controller instance
     */
    public RecommendationController<?, ?> createController(String type, String savePath,
            String version, String loadPath) {
        ControllerCreator<?> creator = controllerRegistry.get(type.toLowerCase());

        if (creator == null) {
            throw new IllegalArgumentException("Unknown controller type: " + type);
        }

        LOGGER.info("Creating controller of type: " + type);
        return creator.create(savePath, version, loadPath);
    }

    /**
     * Functional interface for creating controllers
     */
    @FunctionalInterface
    public interface ControllerCreator<T extends RecommendationController<?, ?>> {
        T create(String savePath, String version, String loadPath);
    }
}
