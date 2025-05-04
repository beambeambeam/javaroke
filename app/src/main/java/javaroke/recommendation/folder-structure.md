# Folder structure

```bash
recommendation/
├── core/
│   ├── models/        # Base data classes (e.g., items, users, interactions).
│   ├── graphs/        # Different graph structures (e.g., HashMap-based, ArrayList-based).
│   ├── utils/         # Lightweight utility functions, scoped to specific graph/model types.
│   ├── algorithms/    # Complex logic & transformation algorithms (e.g., normalization, scoring).
│   ├── version/       # Archived or optimized versions of entire pipelines (refactored code).
│
├── controller/        # Bridge layer that connects core logic (utils/version) to main program.
│
├── evaluation/        # Evaluation logic for comparing models, algorithms, or versions.
│
├── data/              # Input/output data, usually stored as JSON or other serializable format.
│
├── tests/             # Manual and automated tests (triggered via `./gradlew.bat testRec`).

```

## Note

- `core/` is the heart of the recommendation logic.

- `utils/` and `algorithms/` are separate to make it easier to manage reusable code versus specialized logic.

- `version/` allows historical or optimized pipelines to live alongside the latest implementation.

- `controller/` lets you swap out logic layers easily, enabling flexible experimentation.

- `evaluation/` is where you validate ideas and measure performance.

- All testing lives in `tests/`, runnable via a Gradle script to ensure stability.

feat: Add flexible recommendation system.

- properties:
  - Design to handle flexibly based data type. Such as using `graph`, `tree`, or `neural`.
  - Design `utils` and `algoritm` to implement those function based on each based data type to optimize cpu and ram consump.
  - Design `version` and `controller` to
- architecture desigh:
  - `Core/`: For keeping the all calculat process
    - `models/`: For keeping based data type like `HashMapGraph` or any Tree models within interface for each based type. Including the `saves` or `items` data clase.
    - `utils/`: For keeping utilization tools for each based data type like `WeightTransformerForHashMapGraph` or `PerformanceTracker` metrics. Use to clean, manage, and analyze with reuse function.
    - `algorithms/`: For keeping specifig function for each based data type. For example path finding on graph baed data type, If use AI model, it will require model loader.
    - `saves/`: Keep saving function for each data type and algoritm usage. Within back up function that handle those save and delete.
    - `version/`: Separate folder to each based data type. And handle those `utils/`, `algorithms/` usage with version abstract.
  - `Controller/`: Separte folder to each based data type. And handle those `version/` with `saves/` usage. And handle those same based data version with `BasedDataType-Controller`, Then call each version from based data type to use.
  - `datas/`: use to keep saves and back up file.
  - `test/ `: use to develop new function and test without affect to main.
  - `evaluate/`: use to test complete model.
