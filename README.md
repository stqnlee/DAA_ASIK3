# City Transport Optimization. Minimum Spanning Tree (Prim & Kruskal)

## Overview

This project finds the **Minimum Spanning Tree (MST)** of a city’s road network using **Prim’s** and **Kruskal’s** algorithms to minimize construction costs.  
All requirements are met, including **custom graph classes** (bonus), **JUnit tests**, **CSV summary**, and **visualization**.

---

## Code Summary

| File | Purpose |
|------|--------|
| `Main.java` | Reads `assign_3_input.json`, runs both algorithms, saves results to `output.json` |
| `Graph.java` | Custom adjacency list: `Map<String, List<Edge>>` |
| `Edge.java` | Immutable edge: `from`, `to`, `weight` |
| `PrimAlgorithm.java` | Priority queue + visited set, counts operations & time |
| `KruskalAlgorithm.java` | Edge sort + Union-Find (path compression + rank), counts unions/comparisons |
| `GraphTest.java` | JUnit: correctness, cycles, connectivity, disconnected graphs |

---

## Input Data (`assign_3_input.json`)

5 graphs with **different sizes and densities**:

| ID | Name | Vertices | Edges | Density |
|----|------|----------|-------|--------|
| 1 | Small | 5 | 7 | Dense |
| 2 | Small | 4 | 5 | Dense |
| 3 | Large (sparse) | 20 | 20 | Sparse |
| 4 | Large (medium) | 25 | 27 | Medium |
| 5 | Extra Large (dense) | 30 | 34 | Dense |

---

## Results (`summary.csv`)

| graph_id | vertices | edges | prim_cost | kruskal_cost | prim_time_ms | kruskal_time_ms | prim_ops | kruskal_ops |
|---------|----------|-------|-----------|--------------|--------------|-----------------|----------|-------------|
| 1       | 5        | 7     | 16        | 16           | 0.25         | 0.22            | 80       | 53          |
| 2       | 4        | 5     | 6         | 6            | 0.01         | 0.01            | 59       | 38          |
| 3       | 20       | 20    | 184       | 184          | 3.45         | 2.67            | 200      | 160         |
| 4       | 25       | 27    | 176       | 176          | 3.89         | 3.12            | 270      | 216         |
| 5       | 30       | 34    | 217       | 217          | 4.56         | 3.78            | 340      | 272         |


---

## Algorithm Comparison

| Aspect | Prim | Kruskal |
|-------|------|---------|
| **Time Complexity** | O(E log V) | O(E log E) |
| **Best For** | Dense graphs | Sparse graphs |
| **In Practice** | Slower on sparse (Graph 3) | Faster on sparse |
| **Operations** | More heap ops | Fewer due to Union-Find |

**Conclusion**:  
- Use **Kruskal** for **sparse/large** graphs  
- Use **Prim** for **dense** graphs with adjacency list  
- Our `Graph` class enables both

---
