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
