# Optimization of the city's transportation network (Minimum spanning tree)

## Project Structure

```
mst-city-transport/
├── pom.xml
├── src/
│   └── main/
│       └── java/com/mst/
│           ├── model/Graph.java, Edge.java
│           ├── algorithm/PrimAlgorithm.java, KruskalAlgorithm.java
│           └── Main.java
│       └── resources/assign_3_input.json
├── src/test/java/com/mst/GraphTest.java
├── output.json
├── summary.csv
├── json_to_csv.py
├── graph1.dot
├── graph1.png
└── README.md
```

---

## How to Run

### 1. Compile

mvn clean compile


### 2. Run MST Computation

mvn exec:java

→ Creates `output.json` with results for all 5 graphs.

### 3. Generate Summary CSV

python3 json_to_csv.py

→ Creates `summary.csv` (for report)

### 4. Run Tests

mvn test

-> All JUnit tests pass

---

## Input Data (`assign_3_input.json`)

| Graph ID | Name | Vertices | Edges | Density |
|---------|------|----------|-------|--------|
| 1 | Small | 5 | 7 | Dense |
| 2 | Small | 4 | 5 | Dense |
| 3 | Large (sparse) | 20 | 20 | Sparse |
| 4 | Large (medium) | 25 | 27 | Medium |
| 5 | Extra Large (dense) | 30 | 34 | Dense |



## Results (Sample from `summary.csv`)

| graph_id | vertices | edges | prim_cost | kruskal_cost | prim_time_ms | kruskal_time_ms | prim_ops | kruskal_ops |
|---------|----------|-------|-----------|--------------|--------------|-----------------|----------|-------------|
| 1       | 5        | 7     | 16        | 16           | 0.25         | 0.22            | 80       | 53          |
| 2       | 4        | 5     | 6         | 6            | 0.01         | 0.01            | 59       | 38          |
| ...     | ...      | ...   | ...       | ...          | ...          | ...             | ...      | ...         |

MST cost is identical for both algorithms in all cases.

---

## Algorithm Comparison

| Aspect | Prim | Kruskal |
|-------|------|---------|
| **Time Complexity** | O(E log V) | O(E log E) |
| **Best For** | Dense graphs | Sparse graphs |
| **Data Structure** | Priority Queue + Adjacency List | Union-Find + Edge List |
| **In Practice** | Faster on dense | Faster on sparse |

---

## Testing

- **JUnit tests** verify:
  - MST cost equality
  - `V-1` edges
  - No cycles
  - Connected component
  - Disconnected graphs handled
  - Non-negative time/ops

mvn test
# -> BUILD SUCCESS


---

## Bonus: Custom Graph Design

- `Graph.java`: Adjacency list representation
- `Edge.java`: Immutable edge with `from`, `to`, `weight`
- Used by both algorithms
- Visualized with Graphviz (`graph1.dot` → `graph1.png`)
