# Folder strucure

## Main Controller: `controller/MainCrontroller.java`

```bash
recommendation/
├── core/
│   ├── models/        # Base data types (e.g., HashMapGraph, TreeModel), interfaces, data classes
│   ├── utils/         # Utility tools (e.g., transformers, performance trackers)
│   ├── algorithms/    # Core algorithms per data type (e.g., path finding, AI loaders)
│   ├── saves/         # Save, backup, and recovery mechanisms
│   └── version/       # Version-specific logic for each data type
├── controller/
│   └── ...            # Controllers managing data versions, simplified access
├── datas/             # Directory for all saved files and backups
├── test/              # Experimental feature development and isolated testing
└── evaluate/          # Final model evaluation, performance checks
```

## Reccomendation List Usage

```java
  // Initialize the graph controller with a specific version
  HashMapGraphController graphController = new HashMapGraphController("graph.json", "v1");


  // Process the graph
  graphController.process();

  // Get adjacency matrix ArrayList<String> songId;
  AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());


  // Update weight
  graphController.updateData(getExampleQueue());
  graphController.process();

  // Get adjacency matrix ArrayList<String> songId;
  AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());


  // Get log
  System.out.println(graphController.getMetricSummary());
```

## Path to Implement (Step-by-step Flow)

1. **Start from `core/models/`**

   - Implement your base data structures, e.g. `HashMapGraph`, `TreeModel`, etc.
   - Define shared interfaces for consistency across all types (graph, tree, neural).
   - Create essential data classes like `ItemData`, `SaveData`, etc.

2. **Move to `core/utils/`**

   - Add utilities for handling each data type:
     - e.g., `WeightTransformerForHashMapGraph`, `CleanerUtils`, `PerformanceTracker`.
   - Utilities should be optimized for performance and memory usage.

3. **Then go to `core/algorithms/`**

   - Implement algorithms specific to each data structure.
     - For example: Graph path finding, tree traversal, or model prediction logic.
   - Load AI models here if required.

4. **Implement saving logic in `core/saves/`**

   - Build robust save/load functionality.
   - Include auto-backup, delete, and recovery logic based on usage flow.
   - Must be able to handle versioned save states per data type.

5. **Integrate everything in `core/version/`**

   - Create a separate subfolder for each base data type (e.g., `GraphVersion`, `TreeVersion`).
   - Use abstract classes for shared logic and plug in utils/algorithms/saves accordingly.

6. **Connect through `controller/`**

   - Create controllers for each data type (e.g., `GraphController`, `TreeController`).
   - Each controller handles its version’s logic using the structure from `Core/version/` and `Core/saves/`.
   - Use a base interface or abstract for unified control access.

7. **Handle data I/O in `datas/`**

   - This folder stores save files, temporary results, and backups.
   - Make sure the folder structure mirrors the version/data types for clarity.

8. **Test in `test/`**

   - Develop new features or debug issues here safely.
   - Isolated from main logic, so no data corruption.

9. **Evaluate full model performance in `evaluate/`**
   - Use this folder to run benchmarks, comparisons, or final validations.
   - Useful to analyze PerformanceTracker outputs, accuracy, or resource consumption.
