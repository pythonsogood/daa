# Assignment 3. Optimization of a City Transportation Network

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/license/mit/)

## Summary of Input Data and Algorithm Results

### Input Data

In this assignment work with input graph data sets contained in JSON files. Each dataset includes a number of graphs, each graph described by:

* A list of nodes.
* A list of weighted edges.
* For each graph, a corresponding output file contains results of both MST algorithms including the list of edges selected in the MST, the total cost, the number of operations, and an execution time in milliseconds.

### Algorithm Results

| Dataset | Graph Id | Vertices | Edges | Prim's cost | Kruskal's cost | Prim's Operations | Kruskal's Operations | Prim's time | Kruskal's time |
| ------- | -------- | -------- | ----- | ----------- | -------------- | ----------------- | -------------------- | ----------- | -------------- |
| Small   | 1        | 4        | 1     | 4           | 4              | 43                | 5                    | 0.0071      | 0.0092         |
| Small   | 2        | 5        | 9     | 10          | 10             | 103               | 45                   | 0.0039      | 0.008          |
| Small   | 3        | 4        | 5     | 9           | 9              | 62                | 22                   | 0.0117      | 0.0056         |
| Small   | 4        | 4        | 3     | 23          | 23             | 56                | 19                   | 0.003       | 0.009          |
| Small   | 5        | 5        | 4     | 6           | 9              | 77                | 24                   | 0.004       | 0.006          |
| Medium  | 1        | 12       | 26    | 37          | 37             | 537               | 180                  | 0.0249      | 0.0233         |
| Medium  | 2        | 14       | 38    | 27          | 27             | 743               | 276                  | 0.0226      | 0.0212         |
| Medium  | 3        | 10       | 41    | 23          | 23             | 456               | 276                  | 0.0107      | 0.0183         |
| Medium  | 4        | 13       | 56    | 23          | 23             | 722               | 378                  | 0.0127      | 0.0215         |
| Medium  | 5        | 12       | 46    | 24          | 24             | 613               | 327                  | 0.0118      | 0.02           |
| Large   | 1        | 20       | 99    | 32          | 32             | 1595              | 664                  | 0.033       | 0.0692         |
| Large   | 2        | 26       | 28    | 78          | 81             | 1949              | 281                  | 0.0269      | 0.0311         |
| Large   | 3        | 25       | 27    | 82          | 84             | 1717              | 266                  | 0.0249      | 0.0221         |
| Large   | 4        | 26       | 27    | 91          | 91             | 2029              | 279                  | 0.0275      | 0.1514         |
| Large   | 5        | 29       | 251   | 36          | 36             | 3545              | 1888                 | 0.0691      | 0.1343         |


## Comparison between Prim's and Kruskal's Algorithms

### Theoretical Comparison

* Prim's algorithm is a Greedy algorithm, it starts from an arbitrary node and moves through adjacent nodes, by repeatedly adding the smallest‐weight edge that connects a tree‐vertex to a non‐tree vertex. Using an adjacency list, time complexity = O(V<sup>2</sup>).
* Kruskal's algorithm is a Greedy algorithm, it sorts all edges and then processes them in increasing weight, using a union-find (DSU) to avoid cycles. Time complexity = O(E log E + E log V).

### Practical Comparison

* On the small graph example, Kruskal had execution time vs Prim
* As graph size and density increase, Prim's algorithm may become relatively faster.

## Conclusions

* **Sparse graphs**: Kruskal is typically preferable: fewer edges to sort, efficient union‐find operations.

* **Dense graphs**: Prim may become more favourable: when using binary heap, it can handle many edges while growing the tree quickly.

* **Implementation complexity**: if using built‐in priority queue or heap, the Prim implementation is quite simple. If you already have a robust union-find implementation, Kruskal's is clean.

## References

* [Prim's MST Algorithm (GeeksForGeeks)](https://www.geeksforgeeks.org/dsa/prims-minimum-spanning-tree-mst-greedy-algo-5/)
* [Kruskal's MST Algorithm (GeeksForGeeks)](https://www.geeksforgeeks.org/dsa/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/)
* [Google's Gson JSON parsing library](https://github.com/google/gson/)
* [FastCSV CSV parsing/writing library](https://fastcsv.org/)

## Requirements

* [JDK 21](https://jdk.java.net/archive/)
* [Apache Maven](https://maven.apache.org/install.html)
